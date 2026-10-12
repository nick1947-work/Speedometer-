package com.creationunicorn.speedometer.ui

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SpeedometerViewModel by viewModels()
    private var adView: AdView? = null
    private var isPaused = false
    private var currentSelectedTab = 0

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
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupAdBanner()
        setupListeners()
        setupTabs()
        observeViewModel()

        checkFirstTimeFlow()
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
        binding.tabGauge?.setOnClickListener { selectTab(0) }
        binding.tabDigital?.setOnClickListener { selectTab(1) }
        binding.tabMap?.setOnClickListener { selectTab(2) }
        binding.tabNav?.setOnClickListener { selectTab(3) }
    }

    private fun selectTab(index: Int) {
        currentSelectedTab = index
        val tabs = listOfNotNull(binding.tvTabGauge, binding.tvTabDigital, binding.tvTabMap, binding.tvTabNav)
        val indicators = listOfNotNull(binding.indicatorGauge, binding.indicatorDigital, binding.indicatorMap, binding.indicatorNav)

        for (i in tabs.indices) {
            if (i == index) {
                tabs[i].setTextColor(ContextCompat.getColor(this, R.color.neon_cyan))
                if (i < indicators.size) indicators[i].visibility = View.VISIBLE
            } else {
                tabs[i].setTextColor(ContextCompat.getColor(this, R.color.text_gray))
                if (i < indicators.size) indicators[i].visibility = View.INVISIBLE
            }
        }

        when (index) {
            0, 1 -> {
                binding.gaugeViewContainer?.visibility = View.VISIBLE
                binding.includedMapTab?.mapScreenRoot?.visibility = View.GONE
                binding.includedNavTab?.navScreenRoot?.visibility = View.GONE
            }
            2 -> {
                binding.gaugeViewContainer?.visibility = View.GONE
                binding.includedMapTab?.mapScreenRoot?.visibility = View.VISIBLE
                binding.includedNavTab?.navScreenRoot?.visibility = View.GONE
            }
            3 -> {
                binding.gaugeViewContainer?.visibility = View.GONE
                binding.includedMapTab?.mapScreenRoot?.visibility = View.GONE
                binding.includedNavTab?.navScreenRoot?.visibility = View.VISIBLE
            }
        }
    }

    private fun setupAdBanner() {
        binding.adViewContainer?.let { container ->
            adView = AdView(this).apply {
                adUnitId = AdManager.TEST_BANNER_ID
                setAdSize(AdSize.BANNER)
                loadAd(AdRequest.Builder().build())
            }
            container.addView(adView)
        }
    }

    private fun setupListeners() {
        binding.ivSettings?.setOnClickListener {
            startActivity(Intent(this, SettingsActivity::class.java))
        }

        binding.ivHistory?.setOnClickListener {
            startActivity(Intent(this, HistoryActivity::class.java))
        }

        binding.btnMainAction.setOnClickListener {
            startTripFlow()
        }

        binding.btnStopTrip?.setOnClickListener {
            stopTripFlow()
        }

        binding.includedMapTab?.btnMapStop?.setOnClickListener {
            stopTripFlow()
        }

        binding.includedNavTab?.btnNavStop?.setOnClickListener {
            stopTripFlow()
        }

        binding.includedNavTab?.btnNavExit?.setOnClickListener {
            selectTab(0)
        }

        binding.btnResetTripCol?.setOnClickListener {
            viewModel.resetTrip()
            isPaused = false
            updateTripControlState(isActive = false)
        }

        binding.btnPauseTripCol?.setOnClickListener {
            isPaused = !isPaused
            if (isPaused) {
                binding.tvPauseText?.text = "Resume"
                binding.ivPauseIcon?.setImageResource(R.drawable.ic_icon_general_resume)
            } else {
                binding.tvPauseText?.text = "Pause"
                binding.ivPauseIcon?.setImageResource(R.drawable.ic_icon_general_pause)
            }
        }

        binding.ivModeWalk?.setOnClickListener { selectVehicleMode(0) }
        binding.ivModeBicycle?.setOnClickListener { selectVehicleMode(1) }
        binding.ivModeMoto?.setOnClickListener { selectVehicleMode(2) }
        binding.ivModeCar?.setOnClickListener { selectVehicleMode(3) }
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
        val intent = Intent(this, TripSummaryActivity::class.java).apply {
            putExtra(TripSummaryActivity.EXTRA_DISTANCE, unit.formatDistance(metrics.totalDistanceMeters))
            putExtra(TripSummaryActivity.EXTRA_DURATION_MILLIS, metrics.elapsedTimeMillis)
            putExtra(TripSummaryActivity.EXTRA_AVG_SPEED, String.format("%.0f %s", unit.convert(metrics.averageSpeedMps), unit.displayName))
            putExtra(TripSummaryActivity.EXTRA_MAX_SPEED, String.format("%.0f %s", unit.convert(metrics.maxSpeedMps), unit.displayName))
        }
        startActivity(intent)
    }

    private fun updateTripControlState(isActive: Boolean) {
        if (isActive) {
            binding.btnMainAction.visibility = View.GONE
            binding.tripControlBar?.visibility = View.VISIBLE
        } else {
            binding.btnMainAction.visibility = View.VISIBLE
            binding.tripControlBar?.visibility = View.GONE
        }
    }

    private fun selectVehicleMode(mode: Int) {
        val icons = listOfNotNull(binding.ivModeWalk, binding.ivModeBicycle, binding.ivModeMoto, binding.ivModeCar)
        val containers = listOfNotNull(binding.ivModeWalkContainer, binding.ivModeBicycleContainer, binding.ivModeMotoContainer, binding.ivModeCarContainer)

        for (i in icons.indices) {
            if (i == mode) {
                icons[i].setColorFilter(ContextCompat.getColor(this, R.color.neon_cyan))
                if (i < containers.size) containers[i].setBackgroundResource(R.drawable.bg_vehicle_selected)
            } else {
                icons[i].setColorFilter(ContextCompat.getColor(this, R.color.text_dark_gray))
                if (i < containers.size) containers[i].background = null
            }
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

                        // Main Gauge Tab
                        binding.tvMainSpeed.text = String.format("%.0f", currentSpeed)
                        binding.tvMainUnit.text = unit.displayName
                        binding.tvMaxSpeed.text = String.format("%.0f %s", maxSpeed, unit.displayName)
                        binding.tvAvgSpeed.text = String.format("%.0f %s", avgSpeed, unit.displayName)
                        binding.tvDistance.text = unit.formatDistance(metrics.totalDistanceMeters)

                        // Map Tab
                        binding.includedMapTab?.tvMapFloatingSpeed?.text = String.format("%.0f", currentSpeed)
                        binding.includedMapTab?.tvMapDistance?.text = unit.formatDistance(metrics.totalDistanceMeters)
                        binding.includedMapTab?.tvMapMaxSpeed?.text = String.format("%.0f %s", maxSpeed, unit.displayName)
                        binding.includedMapTab?.tvMapAvgSpeed?.text = String.format("%.0f %s", avgSpeed, unit.displayName)

                        // Nav Tab
                        binding.includedNavTab?.tvNavSpeedVal?.text = String.format("%.0f", currentSpeed)
                        binding.includedNavTab?.tvNavDistanceVal?.text = unit.formatDistance(metrics.totalDistanceMeters)
                        binding.includedNavTab?.tvNavAvgVal?.text = String.format("%.0f %s", avgSpeed, unit.displayName)

                        val totalSeconds = metrics.elapsedTimeMillis / 1000
                        val hours = totalSeconds / 3600
                        val minutes = (totalSeconds % 3600) / 60
                        val seconds = totalSeconds % 60
                        val formattedDuration = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                        binding.tvDuration.text = formattedDuration
                        binding.includedMapTab?.tvMapDuration?.text = formattedDuration
                        binding.includedNavTab?.tvNavElapsed?.text = formattedDuration

                        val odoKm = (metrics.totalDistanceMeters / 1000.0).toInt()
                        binding.tvOdometerValue.text = String.format("%06d km", odoKm)

                        if (metrics.isGpsActive) {
                            binding.tvGpsPillText?.text = getString(R.string.gps_status_excellent)
                            binding.ivGpsSignalIcon?.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.neon_green))
                        } else {
                            binding.tvGpsPillText?.text = getString(R.string.gps_status_no)
                            binding.ivGpsSignalIcon?.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.alert_red))
                        }

                        if (metrics.isOverSpeedLimit()) {
                            binding.tvMainSpeed.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.alert_red))
                            triggerVibration()
                        } else {
                            binding.tvMainSpeed.setTextColor(ContextCompat.getColor(this@MainActivity, R.color.neon_cyan))
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

    private fun triggerVibration() {
        val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            vibrator?.vibrate(VibrationEffect.createOneShot(150, VibrationEffect.DEFAULT_AMPLITUDE))
        } else {
            @Suppress("DEPRECATION")
            vibrator?.vibrate(150)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        adView?.destroy()
    }
}
