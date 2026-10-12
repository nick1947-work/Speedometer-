package com.creationunicorn.speedometer.ui

import android.Manifest
import android.content.Context
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
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: SpeedometerViewModel by viewModels()
    private var adView: AdView? = null

    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val fineGranted = permissions[Manifest.permission.ACCESS_FINE_LOCATION] ?: false
        if (fineGranted) {
            viewModel.startTracking()
            // After system permission granted, show Battery / Auto Start Optimization dialog
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
            // Step 1: Show Custom Location Permission Dialog
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
            // Show first-time feature tour overlay
            binding.tourLayout.tourOverlayRoot.visibility = View.VISIBLE
            binding.tourLayout.btnTourNext.setOnClickListener {
                binding.tourLayout.tourOverlayRoot.visibility = View.GONE
                prefs.edit().putBoolean("seen_feature_tour", true).apply()
            }
        }
    }

    private fun setupTabs() {
        binding.tabGauge.setOnClickListener { selectTab(0) }
        binding.tabDigital.setOnClickListener { selectTab(1) }
        binding.tabMap.setOnClickListener { selectTab(2) }
        binding.tabNav.setOnClickListener { selectTab(3) }
    }

    private fun selectTab(index: Int) {
        val tabs = listOf(binding.tvTabGauge, binding.tvTabDigital, binding.tvTabMap, binding.tvTabNav)
        val indicators = listOf(binding.indicatorGauge, binding.indicatorDigital, binding.indicatorMap, binding.indicatorNav)

        for (i in tabs.indices) {
            if (i == index) {
                tabs[i].setTextColor(ContextCompat.getColor(this, R.color.neon_cyan))
                indicators[i].visibility = View.VISIBLE
            } else {
                tabs[i].setTextColor(ContextCompat.getColor(this, R.color.text_gray))
                indicators[i].visibility = View.INVISIBLE
            }
        }
    }

    private fun setupAdBanner() {
        adView = AdView(this).apply {
            adUnitId = AdManager.TEST_BANNER_ID
            setAdSize(AdSize.BANNER)
            loadAd(AdRequest.Builder().build())
        }
        binding.adViewContainer.addView(adView)
    }

    private fun setupListeners() {
        binding.btnMainAction.setOnClickListener {
            if (viewModel.isTripActive.value) {
                AdManager.showInterstitial(this) {
                    viewModel.stopTrip()
                    TrackingService.stop(this)
                    Toast.makeText(this, getString(R.string.trip_saved_message), Toast.LENGTH_SHORT).show()
                }
            } else {
                viewModel.startTrip()
                TrackingService.start(this)
            }
        }

        binding.ivModeWalk.setOnClickListener { selectVehicleMode(0) }
        binding.ivModeBicycle.setOnClickListener { selectVehicleMode(1) }
        binding.ivModeMoto.setOnClickListener { selectVehicleMode(2) }
        binding.ivModeCar.setOnClickListener { selectVehicleMode(3) }
    }

    private fun selectVehicleMode(mode: Int) {
        val icons = listOf(binding.ivModeWalk, binding.ivModeBicycle, binding.ivModeMoto, binding.ivModeCar)
        for (i in icons.indices) {
            if (i == mode) {
                icons[i].setColorFilter(ContextCompat.getColor(this, R.color.neon_cyan))
            } else {
                icons[i].setColorFilter(ContextCompat.getColor(this, R.color.text_dark_gray))
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

                        binding.tvMainSpeed.text = String.format("%.0f", currentSpeed)
                        binding.tvMainUnit.text = unit.displayName
                        binding.tvMaxSpeed.text = String.format("%.0f %s", maxSpeed, unit.displayName)
                        binding.tvAvgSpeed.text = String.format("%.0f %s", avgSpeed, unit.displayName)
                        binding.tvDistance.text = unit.formatDistance(metrics.totalDistanceMeters)

                        val totalSeconds = metrics.elapsedTimeMillis / 1000
                        val hours = totalSeconds / 3600
                        val minutes = (totalSeconds % 3600) / 60
                        val seconds = totalSeconds % 60
                        binding.tvDuration.text = String.format("%02d:%02d:%02d", hours, minutes, seconds)

                        val odoKm = (metrics.totalDistanceMeters / 1000.0).toInt()
                        binding.tvOdometerValue.text = String.format("%06d km", odoKm)

                        if (metrics.isGpsActive) {
                            binding.tvGpsPillText.text = getString(R.string.gps_status_excellent)
                            binding.ivGpsSignalIcon.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.neon_green))
                        } else {
                            binding.tvGpsPillText.text = getString(R.string.gps_status_no)
                            binding.ivGpsSignalIcon.setColorFilter(ContextCompat.getColor(this@MainActivity, R.color.alert_red))
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
                        if (isActive) {
                            binding.btnMainAction.text = "■  STOP"
                            binding.btnMainAction.setBackgroundResource(R.drawable.bg_button_start_pill)
                        } else {
                            binding.btnMainAction.text = "▶  START"
                            binding.btnMainAction.setBackgroundResource(R.drawable.bg_button_start_pill)
                        }
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
