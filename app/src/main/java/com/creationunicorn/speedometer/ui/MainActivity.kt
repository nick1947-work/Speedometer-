package com.creationunicorn.speedometer.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.graphics.Color
import android.media.AudioManager
import android.media.ToneGenerator
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.preference.PreferenceManager
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.ads.AdManager
import com.creationunicorn.speedometer.databinding.ActivityMainBinding
import com.creationunicorn.speedometer.service.TrackingService
import com.creationunicorn.speedometer.ui.dialog.PermissionDialogHelper
import com.creationunicorn.speedometer.ui.history.HistoryActivity
import com.creationunicorn.speedometer.ui.settings.SettingsActivity
import com.creationunicorn.speedometer.ui.trip.TripSummaryActivity
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.creationunicorn.speedometer.data.TripRecord
import com.creationunicorn.speedometer.data.TripRepository

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SpeedometerViewModel by viewModels()
    private var adView: AdView? = null
    private var isPaused = false
    private var currentSelectedTab = 0
    private var currentSelectedVehicle = 0
    private var toneGenerator: ToneGenerator? = null
    private var lastAlertTime = 0L

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        if (fineGranted) {
            viewModel.startTracking()
            checkAndShowBatteryDialog()
        } else {
            Toast.makeText(this, "Location permission is required for accurate speed", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Initialize OSM configuration
        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        Configuration.getInstance().userAgentValue = packageName

        if (savedInstanceState != null) {
            currentSelectedTab = savedInstanceState.getInt("saved_selected_tab", 0)
            currentSelectedVehicle = savedInstanceState.getInt("saved_selected_vehicle", 0)
            isPaused = savedInstanceState.getBoolean("saved_is_paused", false)
            val savedHud = savedInstanceState.getBoolean("saved_is_hud", false)
            if (savedHud && !viewModel.isHudMode.value) {
                viewModel.toggleHudMode()
            }
        }

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initMapViews()
        setupAdBanner()
        setupListeners()
        setupTabs()
        setupVehicleModes()
        observeViewModel()

        if (viewModel.isTripActive.value) {
            updateTripControlState(isActive = true)
            if (isPaused) {
                binding.tvPauseText.text = "Resume"
                binding.ivPauseIcon.setImageResource(R.drawable.ic_icon_general_resume)
            }
        }

        checkFirstTimeFlow()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt("saved_selected_tab", currentSelectedTab)
        outState.putInt("saved_selected_vehicle", currentSelectedVehicle)
        outState.putBoolean("saved_is_paused", isPaused)
        outState.putBoolean("saved_is_hud", viewModel.isHudMode.value)
    }

    private fun initMapViews() {
        val defaultPoint = GeoPoint(28.5967, 77.3840) // Sector 70 Noida from screenshot 4 & 5

        binding.tabMapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(16.5)
            controller.setCenter(defaultPoint)
        }

        binding.navMapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(16.5)
            controller.setCenter(defaultPoint)
        }
    }

    private fun checkFirstTimeFlow() {
        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        val hasSeenLocationDialog = prefs.getBoolean("seen_location_dialog", false)
        val hasFineLoc = ContextCompat.checkSelfPermission(
            this, Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (!hasFineLoc && !hasSeenLocationDialog) {
            PermissionDialogHelper.showLocationPermissionDialog(
                this,
                onGrantClicked = {
                    prefs.edit().putBoolean("seen_location_dialog", true).apply()
                    requestSystemLocation()
                },
                onLaterClicked = {
                    prefs.edit().putBoolean("seen_location_dialog", true).apply()
                    checkAndShowFeatureTour()
                }
            )
        } else if (!hasFineLoc) {
            requestSystemLocation()
        } else {
            viewModel.startTracking()
            checkAndShowFeatureTour()
        }
    }

    private fun requestSystemLocation() {
        val permissions = mutableListOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions.add(Manifest.permission.POST_NOTIFICATIONS)
        }
        permissionLauncher.launch(permissions.toTypedArray())
    }

    private fun checkAndShowBatteryDialog() {
        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        val hasSeenBattery = prefs.getBoolean("seen_battery_dialog", false)
        if (!hasSeenBattery) {
            prefs.edit().putBoolean("seen_battery_dialog", true).apply()
            PermissionDialogHelper.showPermissionRequiredDialog(this) {
                checkAndShowFeatureTour()
            }
        } else {
            checkAndShowFeatureTour()
        }
    }

    private fun checkAndShowFeatureTour() {
        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        val hasSeenTour = prefs.getBoolean("seen_feature_tour", false)
        if (!hasSeenTour) {
            val tourLayout = binding.tourLayout ?: return
            tourLayout.tourOverlayRoot.visibility = View.VISIBLE
            tourLayout.tourStep1Container.visibility = View.VISIBLE
            tourLayout.tourStep2Container.visibility = View.GONE

            tourLayout.btnTourStep1Next.setOnClickListener {
                tourLayout.tourStep1Container.visibility = View.GONE
                tourLayout.tourStep2Container.visibility = View.VISIBLE
            }

            tourLayout.btnTourStep2GotIt.setOnClickListener {
                tourLayout.tourOverlayRoot.visibility = View.GONE
                prefs.edit().putBoolean("seen_feature_tour", true).apply()
            }
        }
    }

    private fun setupTabs() {
        binding.tabGauge.setOnClickListener { selectTab(0) }
        binding.tabDigital.setOnClickListener { selectTab(1) }
        binding.tabMap.setOnClickListener { selectTab(2) }
        binding.tabNav.setOnClickListener { selectTab(3) }
        selectTab(0)
    }

    private fun selectTab(index: Int) {
        currentSelectedTab = index
        val tabs = listOf(binding.tvTabGauge, binding.tvTabDigital, binding.tvTabMap, binding.tvTabNav)
        val indicators = listOf(binding.indicatorGauge, binding.indicatorDigital, binding.indicatorMap, binding.indicatorNav)

        val cyanColor = ContextCompat.getColor(this, R.color.neon_cyan)
        val grayColor = ContextCompat.getColor(this, R.color.text_gray)

        for (i in tabs.indices) {
            if (i == index) {
                tabs[i].setTextColor(cyanColor)
                indicators[i].visibility = View.VISIBLE
            } else {
                tabs[i].setTextColor(grayColor)
                indicators[i].visibility = View.INVISIBLE
            }
        }

        when (index) {
            0 -> { // Gauge Tab
                binding.mainTabsBodyContainer.visibility = View.VISIBLE
                binding.viewNavContainer.visibility = View.GONE
                binding.viewGaugeDisplay.visibility = View.VISIBLE
                binding.viewDigitalDisplay.visibility = View.GONE
                binding.viewMapDisplay.visibility = View.GONE
                binding.ivFloatingWindow.visibility = View.VISIBLE
            }
            1 -> { // Digital Tab
                binding.mainTabsBodyContainer.visibility = View.VISIBLE
                binding.viewNavContainer.visibility = View.GONE
                binding.viewGaugeDisplay.visibility = View.GONE
                binding.viewDigitalDisplay.visibility = View.VISIBLE
                binding.viewMapDisplay.visibility = View.GONE
                binding.ivFloatingWindow.visibility = View.VISIBLE
            }
            2 -> { // Map Tab
                binding.mainTabsBodyContainer.visibility = View.VISIBLE
                binding.viewNavContainer.visibility = View.GONE
                binding.viewGaugeDisplay.visibility = View.GONE
                binding.viewDigitalDisplay.visibility = View.GONE
                binding.viewMapDisplay.visibility = View.VISIBLE
                binding.ivFloatingWindow.visibility = View.VISIBLE
            }
            3 -> { // Nav Tab
                binding.mainTabsBodyContainer.visibility = View.GONE
                binding.viewNavContainer.visibility = View.VISIBLE
                binding.ivFloatingWindow.visibility = View.GONE // Matches Screenshot 5!
            }
        }
    }

    private fun setupVehicleModes() {
        val clickWalk = View.OnClickListener { selectVehicleMode(0) }
        val clickBike = View.OnClickListener { selectVehicleMode(1) }
        val clickMotor = View.OnClickListener { selectVehicleMode(2) }
        val clickCar = View.OnClickListener { selectVehicleMode(3) }

        binding.walkMode.setOnClickListener(clickWalk)
        binding.ivWalkMode.setOnClickListener(clickWalk)

        binding.bikeMode.setOnClickListener(clickBike)
        binding.ivBikeMode.setOnClickListener(clickBike)

        binding.motorMode.setOnClickListener(clickMotor)
        binding.ivMotorMode.setOnClickListener(clickMotor)

        binding.carMode.setOnClickListener(clickCar)
        binding.ivCarMode.setOnClickListener(clickCar)

        selectVehicleMode(currentSelectedVehicle)
    }

    private fun selectVehicleMode(mode: Int) {
        currentSelectedVehicle = mode
        val modes = listOf(binding.walkMode, binding.bikeMode, binding.motorMode, binding.carMode)
        val icons = listOf(binding.ivWalkMode, binding.ivBikeMode, binding.ivMotorMode, binding.ivCarMode)

        val cyanColor = ContextCompat.getColor(this, R.color.neon_cyan)
        val dimColor = Color.parseColor("#55FFFFFF")

        for (i in modes.indices) {
            if (i == mode) {
                modes[i].setBackgroundResource(R.drawable.bg_vehicle_selected)
                icons[i].setColorFilter(cyanColor)
            } else {
                modes[i].background = null
                icons[i].setColorFilter(dimColor)
            }
        }

        when (mode) {
            0 -> binding.gaugeSpeedometer.setSpeedMax(15f) // Walk ~15 km/h
            1 -> binding.gaugeSpeedometer.setSpeedMax(40f) // Bike ~40 km/h
            2 -> binding.gaugeSpeedometer.setSpeedMax(120f) // Motorcycle ~120 km/h
            3 -> binding.gaugeSpeedometer.setSpeedMax(140f) // Car ~140 km/h
        }
    }

    private fun setupAdBanner() {
        binding.adViewContainer.let { container ->
            adView = AdView(this).apply {
                adUnitId = AdManager.TEST_BANNER_ID
                setAdSize(AdSize.BANNER)
                loadAd(AdRequest.Builder().build())
            }
            container.addView(adView)
        }
    }

    private fun setupListeners() {
        binding.ivSettings.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.ivCrown.setOnClickListener {
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("👑 Speedometer VIP")
                .setMessage("Upgrade to VIP for the ultimate experience:\n\n• 100% Ad-Free\n• HUD Windshield Mirror Mode\n• Unlimited GPX/KML Route Export\n• Offline Topo & Satellite Maps\n• Custom Theme Colors")
                .setPositiveButton("Get VIP") { _, _ ->
                    Toast.makeText(this, "VIP unlocked for testing!", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Maybe Later", null)
                .show()
        }

        binding.ivFloatingWindow.setOnClickListener {
            val isLandscape = resources.configuration.orientation == android.content.res.Configuration.ORIENTATION_LANDSCAPE
            if (isLandscape) {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
            } else {
                requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
            }
        }

        binding.ivHudMode.setOnClickListener {
            viewModel.toggleHudMode()
        }

        binding.ivHistory.setOnClickListener {
            binding.ivHistory.notify = false
            val intent = Intent(this, HistoryActivity::class.java).apply {
                putExtra(HistoryActivity.EXTRA_SELECTED_MODE, currentSelectedVehicle)
            }
            startActivity(intent)
        }

        binding.btnMainAction.setOnClickListener {
            startTripFlow()
        }

        binding.btnStopTrip.setOnClickListener {
            stopTripFlow()
        }

        binding.btnResetTripCol.setOnClickListener {
            viewModel.resetTrip()
            isPaused = false
            updateTripControlState(isActive = false)
        }

        binding.btnPauseTripCol.setOnClickListener {
            isPaused = !isPaused
            if (isPaused) {
                binding.tvPauseText.text = "Resume"
                binding.ivPauseIcon.setImageResource(R.drawable.ic_icon_general_resume)
            } else {
                binding.tvPauseText.text = "Pause"
                binding.ivPauseIcon.setImageResource(R.drawable.ic_icon_general_pause)
            }
        }

        // Map tab controls
        var isTopoMap = false
        val mapStyleListener = View.OnClickListener {
            isTopoMap = !isTopoMap
            val tileSource = if (isTopoMap) TileSourceFactory.OpenTopo else TileSourceFactory.MAPNIK
            binding.tabMapView.setTileSource(tileSource)
            binding.navMapView.setTileSource(tileSource)
            val name = if (isTopoMap) "Topographic / Terrain" else "Standard Street"
            Toast.makeText(this, "Map layer: $name", Toast.LENGTH_SHORT).show()
        }
        binding.btnMapChangeStyle.setOnClickListener(mapStyleListener)
        binding.btnNavChangeStyle.setOnClickListener(mapStyleListener)

        val limits = listOf("30 km", "50 km", "100 km", "No Limit")
        var limitIdx = 0
        binding.btnNavDistance.setOnClickListener {
            limitIdx = (limitIdx + 1) % limits.size
            Toast.makeText(this, "Navigation limit: ${limits[limitIdx]}", Toast.LENGTH_SHORT).show()
        }

        binding.btnMapRecenter.setOnClickListener {
            val loc = viewModel.metrics.value
            if (loc.latitude != 0.0 || loc.longitude != 0.0) {
                binding.tabMapView.controller.animateTo(GeoPoint(loc.latitude, loc.longitude))
            } else {
                binding.tabMapView.controller.animateTo(GeoPoint(28.5967, 77.3840))
            }
        }

        binding.btnNavRecenter.setOnClickListener {
            val loc = viewModel.metrics.value
            if (loc.latitude != 0.0 || loc.longitude != 0.0) {
                binding.navMapView.controller.animateTo(GeoPoint(loc.latitude, loc.longitude))
            } else {
                binding.navMapView.controller.animateTo(GeoPoint(28.5967, 77.3840))
            }
        }

        binding.llSearchDestination.setOnClickListener {
            val input = android.widget.EditText(this).apply {
                hint = "Enter destination address..."
                setTextColor(Color.WHITE)
                setHintTextColor(Color.GRAY)
                setBackgroundColor(Color.parseColor("#22242A"))
                setPadding(32, 24, 32, 24)
            }
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Set Destination")
                .setView(input)
                .setPositiveButton("Navigate") { _, _ ->
                    val dest = input.text.toString().trim()
                    if (dest.isNotEmpty()) {
                        binding.tvNavCoordinates.text = dest
                        Toast.makeText(this, "Destination set: $dest", Toast.LENGTH_SHORT).show()
                    }
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        binding.cardNavHome.setOnClickListener {
            binding.tvNavCoordinates.text = "Home • 24 km remaining"
            Toast.makeText(this, "Route set to Home", Toast.LENGTH_SHORT).show()
        }

        binding.cardNavCompany.setOnClickListener {
            binding.tvNavCoordinates.text = "Company Office • 12 km remaining"
            Toast.makeText(this, "Route set to Company", Toast.LENGTH_SHORT).show()
        }

        binding.cardNavMore.setOnClickListener {
            val places = arrayOf("⛽ Gas Station", "🅿️ Parking Lot", "☕ Restaurant / Cafe", "🏥 Hospital", "✈️ Airport")
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Quick Destinations")
                .setItems(places) { _, which ->
                    binding.tvNavCoordinates.text = "${places[which]} • Calculating route..."
                    Toast.makeText(this, "Route set to ${places[which]}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }
    }

    private fun startTripFlow() {
        viewModel.startTrip()
        TrackingService.start(this)
        isPaused = false
        updateTripControlState(isActive = true)
    }

    private fun stopTripFlow() {
        AdManager.showInterstitial(this) {
            viewModel.stopTrip()
            TrackingService.stop(this)
            updateTripControlState(isActive = false)
            openTripSummary()
        }
    }

    private fun openTripSummary() {
        val metrics = viewModel.metrics.value
        val unit = viewModel.unit.value
        val distVal = if (unit.displayName == "knot") {
            metrics.totalDistanceMeters / 1852.0
        } else {
            unit.convertDistance(metrics.totalDistanceMeters)
        }
        val distStr = if (distVal >= 10) String.format(Locale.US, "%.0f", distVal) else String.format(Locale.US, "%.1f", distVal)
        val finalDistStr = if (distStr == "0.0") "0" else distStr

        val avgSpeed = String.format(Locale.US, "%.0f", unit.convert(metrics.averageSpeedMps))
        val maxSpeed = String.format(Locale.US, "%.0f", unit.convert(metrics.maxSpeedMps))
        val distUnitStr = if (unit.displayName == "knot") "nm" else unit.distanceDisplayName

        val tripNum = TripRepository.getNextTripNumber(this)
        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.US).format(Date())
        val totalSec = metrics.elapsedTimeMillis / 1000
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        val durationStr = String.format(Locale.US, "%02d:%02d:%02d", h, m, s)

        val newTrip = TripRecord(
            id = System.currentTimeMillis().toString(),
            title = "Trip-$tripNum",
            vehicleMode = currentSelectedVehicle,
            distance = finalDistStr,
            distanceUnit = distUnitStr,
            avgSpeed = avgSpeed,
            maxSpeed = maxSpeed,
            speedUnit = unit.displayName,
            durationStr = durationStr,
            durationMillis = metrics.elapsedTimeMillis,
            dateStr = currentDate,
            fromTime = currentTime,
            toTime = currentTime,
            fromAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
            toAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
            latitude = metrics.latitude,
            longitude = metrics.longitude
        )
        TripRepository.saveTrip(this, newTrip)

        val intent = Intent(this, TripSummaryActivity::class.java).apply {
            putExtra(TripSummaryActivity.EXTRA_TRIP_ID, newTrip.id)
            putExtra(TripSummaryActivity.EXTRA_TITLE, newTrip.title)
            putExtra(TripSummaryActivity.EXTRA_DISTANCE, finalDistStr)
            putExtra(TripSummaryActivity.EXTRA_DISTANCE_UNIT, distUnitStr)
            putExtra(TripSummaryActivity.EXTRA_DURATION_MILLIS, metrics.elapsedTimeMillis)
            putExtra(TripSummaryActivity.EXTRA_AVG_SPEED, avgSpeed)
            putExtra(TripSummaryActivity.EXTRA_MAX_SPEED, maxSpeed)
            putExtra(TripSummaryActivity.EXTRA_SPEED_UNIT, unit.displayName)
            putExtra(TripSummaryActivity.EXTRA_MODE, currentSelectedVehicle)
            putExtra(TripSummaryActivity.EXTRA_LAT, metrics.latitude)
            putExtra(TripSummaryActivity.EXTRA_LON, metrics.longitude)
            putExtra(TripSummaryActivity.EXTRA_DATE, newTrip.dateStr)
            putExtra(TripSummaryActivity.EXTRA_FROM_TIME, newTrip.fromTime)
            putExtra(TripSummaryActivity.EXTRA_TO_TIME, newTrip.toTime)
            putExtra(TripSummaryActivity.EXTRA_FROM_ADDRESS, newTrip.fromAddress)
            putExtra(TripSummaryActivity.EXTRA_TO_ADDRESS, newTrip.toAddress)
        }
        startActivity(intent)
    }

    private fun updateTripControlState(isActive: Boolean) {
        if (isActive) {
            binding.btnMainAction.visibility = View.GONE
            binding.tripControlBar.visibility = View.VISIBLE
        } else {
            binding.btnMainAction.visibility = View.VISIBLE
            binding.tripControlBar.visibility = View.GONE
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.metrics.collect { metrics ->
                        val unit = viewModel.unit.value
                        val currentSpeed = unit.convert(metrics.currentSpeedMps)
                        val maxSpeed = unit.convert(metrics.maxSpeedMps)
                        val avgSpeed = unit.convert(metrics.averageSpeedMps)

                        val speedStr = String.format(Locale.US, "%.0f", currentSpeed)
                        val maxSpeedStr = String.format(Locale.US, "%.0f", maxSpeed)
                        val avgSpeedStr = String.format(Locale.US, "%.0f", avgSpeed)

                        // 1. Gauge Tab Readouts
                        binding.digitalGaugeSpeed.text = speedStr
                        binding.tvGaugeUnit.text = unit.displayName
                        binding.gaugeSpeedometer.currentSpeed = currentSpeed.toFloat()

                        val odoNm = (metrics.totalDistanceMeters / 1852.0).toInt()
                        val odoStr = String.format(Locale.US, "%06d", odoNm)
                        binding.digitalGaugeOdometer.digitalColor = Color.parseColor("#6C757D")
                        binding.digitalGaugeOdometer.text = odoStr
                        binding.tvGaugeOdoUnit.text = if (unit.displayName == "knot") "nm" else unit.distanceDisplayName

                        // 2. Digital Tab Readouts
                        binding.digitalSpeedLarge.text = speedStr
                        binding.tvDigitalUnit.text = unit.displayName
                        binding.digitalOdometerVal.digitalColor = Color.parseColor("#6C757D")
                        binding.digitalOdometerVal.text = odoStr
                        binding.tvDigitalOdoUnit.text = if (unit.displayName == "knot") "nm" else unit.distanceDisplayName

                        // 3. Map Tab Readout
                        binding.digitalMapSpeedChip.text = speedStr

                        // 4. 2x2 Metrics Panel Readouts
                        val totalSeconds = metrics.elapsedTimeMillis / 1000
                        val hours = totalSeconds / 3600
                        val minutes = (totalSeconds % 3600) / 60
                        val seconds = totalSeconds % 60
                        val formattedDuration = String.format(Locale.US, "%02d:%02d:%02d", hours, minutes, seconds)

                        binding.digitalDuration.text = formattedDuration

                        val distVal = if (unit.displayName == "knot") {
                            metrics.totalDistanceMeters / 1852.0
                        } else {
                            unit.convertDistance(metrics.totalDistanceMeters)
                        }
                        val distStr = if (distVal >= 10) String.format(Locale.US, "%.0f", distVal) else String.format(Locale.US, "%.1f", distVal)
                        binding.digitalDistance.text = if (distStr == "0.0") "0" else distStr
                        binding.tvDistanceUnit.text = if (unit.displayName == "knot") "nm" else unit.distanceDisplayName

                        binding.digitalAvgSpeed.text = avgSpeedStr
                        binding.tvAvgSpeedUnit.text = unit.displayName

                        binding.digitalMaxSpeed.text = maxSpeedStr
                        binding.tvMaxSpeedUnit.text = unit.displayName

                        // Coordinates
                        if (metrics.latitude != 0.0 || metrics.longitude != 0.0) {
                            val coordStr = formatCoordinate(metrics.latitude, metrics.longitude)
                            binding.tvMapCoordinates.text = coordStr
                            binding.tvNavCoordinates.text = coordStr

                            val geoPoint = GeoPoint(metrics.latitude, metrics.longitude)
                            binding.tabMapView.controller.setCenter(geoPoint)
                            binding.navMapView.controller.setCenter(geoPoint)
                        }

                        // GPS Status Top Bar
                        if (metrics.isGpsActive) {
                            binding.ivGpsSignalIcon.setImageResource(R.drawable.ic_icon_gps_excellent)
                            binding.tvGpsStatusDesc.text = "GPS: Excellent"
                            binding.tvGpsSatellites.text = "(8/12 Satellites)"
                        } else {
                            binding.ivGpsSignalIcon.setImageResource(R.drawable.ic_icon_gps_no)
                            binding.tvGpsStatusDesc.text = "GPS: No"
                            binding.tvGpsSatellites.text = "(0/0 Satellites)"
                        }

                        // Overspeed Alert
                        val alertColor = ContextCompat.getColor(this@MainActivity, R.color.alert_red)
                        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
                        val themeHex = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
                        val normalThemeColor = try { Color.parseColor(themeHex) } catch (e: Exception) { ContextCompat.getColor(this@MainActivity, R.color.neon_cyan) }
                        val isWarningEnabled = prefs.getBoolean("pref_speed_warning", true)
                        val isSoundEnabled = prefs.getBoolean("pref_sound", true)
                        val isVibrationEnabled = prefs.getBoolean("pref_vibration", true)

                        if (isWarningEnabled && metrics.isOverSpeedLimit()) {
                            binding.digitalGaugeSpeed.digitalColor = alertColor
                            binding.digitalSpeedLarge.digitalColor = alertColor
                            val now = System.currentTimeMillis()
                            if (now - lastAlertTime > 1500L) {
                                lastAlertTime = now
                                if (isSoundEnabled) {
                                    triggerSpeedAlarmSound()
                                }
                                if (isVibrationEnabled) {
                                    triggerVibration()
                                }
                            }
                        } else {
                            binding.digitalGaugeSpeed.digitalColor = normalThemeColor
                            binding.digitalSpeedLarge.digitalColor = normalThemeColor
                        }
                    }
                }

                launch {
                    viewModel.isHudMode.collect { isHud ->
                        binding.upperDisplayContainer.scaleX = if (isHud) -1f else 1f
                        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
                        val themeHex = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
                        val activeColor = try { Color.parseColor(themeHex) } catch (e: Exception) { ContextCompat.getColor(this@MainActivity, R.color.neon_cyan) }
                        if (isHud) {
                            binding.ivHudMode.setColorFilter(activeColor)
                            Toast.makeText(this@MainActivity, "HUD Mirror Mode ON: Place device on dashboard facing windshield", Toast.LENGTH_SHORT).show()
                        } else {
                            binding.ivHudMode.clearColorFilter()
                        }
                    }
                }

                launch {
                    viewModel.isTripActive.collect { isActive ->
                        updateTripControlState(isActive)
                    }
                }
            }
        }
    }

    private fun formatCoordinate(lat: Double, lon: Double): String {
        val latHem = if (lat >= 0) "N" else "S"
        val lonHem = if (lon >= 0) "E" else "W"
        val absLat = Math.abs(lat)
        val absLon = Math.abs(lon)
        val latD = absLat.toInt()
        val latM = ((absLat - latD) * 60).toInt()
        val latS = ((absLat - latD - latM / 60.0) * 3600)
        val lonD = absLon.toInt()
        val lonM = ((absLon - lonD) * 60).toInt()
        val lonS = ((absLon - lonD - lonM / 60.0) * 3600)
        return String.format(Locale.US, "%d°%d'%.1f\"%s , %d°%d'%.1f\"%s", latD, latM, latS, latHem, lonD, lonM, lonS, lonHem)
    }

    private fun triggerSpeedAlarmSound() {
        try {
            if (toneGenerator == null) {
                toneGenerator = ToneGenerator(AudioManager.STREAM_NOTIFICATION, 85)
            }
            toneGenerator?.startTone(ToneGenerator.TONE_PROP_BEEP2, 250)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun triggerVibration() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator ?: return
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator.vibrate(VibrationEffect.createWaveform(longArrayOf(0, 150, 80, 150), -1))
        } else {
            @Suppress("DEPRECATION")
            vibrator.vibrate(250)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.tabMapView.onResume()
        binding.navMapView.onResume()

        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        val savedUnit = prefs.getString("speed_unit", "km")
        val targetUnit = when (savedUnit) {
            "mph" -> com.creationunicorn.speedometer.domain.SpeedUnit.MPH
            "knot" -> com.creationunicorn.speedometer.domain.SpeedUnit.KNOTS
            else -> com.creationunicorn.speedometer.domain.SpeedUnit.KMH
        }
        viewModel.setUnit(targetUnit)

        val isWarningEnabled = prefs.getBoolean("pref_speed_warning", true)
        val warningLimit = prefs.getFloat("pref_speed_warning_limit", 80f)
        val warningLimitKmh = when (savedUnit) {
            "mph" -> warningLimit * 1.60934f
            "knot" -> warningLimit * 1.852f
            else -> warningLimit
        }
        viewModel.setSpeedLimit(if (isWarningEnabled) warningLimitKmh else 0f)

        val keepScreenOn = prefs.getBoolean("pref_keep_screen_on", true)
        if (keepScreenOn) {
            window.addFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        } else {
            window.clearFlags(android.view.WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }

        val themeHex = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
        try {
            val themeColor = Color.parseColor(themeHex)
            binding.tvGaugeUnit.setTextColor(themeColor)
            binding.tvDigitalUnit.setTextColor(themeColor)
            binding.tvDistanceUnit.setTextColor(themeColor)
            if (viewModel.isHudMode.value) {
                binding.ivHudMode.setColorFilter(themeColor)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        binding.tabMapView.onPause()
        binding.navMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        toneGenerator?.release()
        toneGenerator = null
        adView?.destroy()
    }
}
