package com.creationunicorn.speedometer.ui.settings

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.LocationManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.creationunicorn.speedometer.databinding.ActivityPermissionSettingsBinding

class PermissionSettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPermissionSettingsBinding

    private val locationPermissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) {
        checkPermissions()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPermissionSettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackPermission.setOnClickListener {
            finish()
        }

        binding.btnTurnOnGps.setOnClickListener {
            val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
            startActivity(intent)
        }

        binding.btnAllowLocation.setOnClickListener {
            locationPermissionLauncher.launch(
                arrayOf(
                    Manifest.permission.ACCESS_FINE_LOCATION,
                    Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }

        binding.btnBatterySettings.setOnClickListener {
            try {
                val intent = Intent(Settings.ACTION_IGNORE_BATTERY_OPTIMIZATION_SETTINGS)
                startActivity(intent)
            } catch (e: Exception) {
                openAppDetailsSettings()
            }
        }

        binding.btnAutoStartSettings.setOnClickListener {
            openAppDetailsSettings()
        }
    }

    override fun onResume() {
        super.onResume()
        checkPermissions()
    }

    private fun checkPermissions() {
        // 1. Check GPS
        val locationManager = getSystemService(Context.LOCATION_SERVICE) as? LocationManager
        val isGpsOn = locationManager?.isProviderEnabled(LocationManager.GPS_PROVIDER) == true

        if (isGpsOn) {
            binding.tvGpsStatus.text = "✓ On"
            binding.tvGpsStatus.setTextColor(Color.parseColor("#00E676"))
            binding.btnTurnOnGps.visibility = View.GONE
        } else {
            binding.tvGpsStatus.text = "Off"
            binding.tvGpsStatus.setTextColor(Color.parseColor("#FF5252"))
            binding.btnTurnOnGps.visibility = View.VISIBLE
        }

        // 2. Check Location Permission
        val fineGranted = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (fineGranted) {
            binding.tvLocationPermStatus.text = "✓ Allowed"
            binding.tvLocationPermStatus.setTextColor(Color.parseColor("#00E676"))
            binding.btnAllowLocation.visibility = View.GONE
        } else {
            binding.tvLocationPermStatus.text = "Denied"
            binding.tvLocationPermStatus.setTextColor(Color.parseColor("#FF5252"))
            binding.btnAllowLocation.visibility = View.VISIBLE
        }
    }

    private fun openAppDetailsSettings() {
        val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
            data = Uri.fromParts("package", packageName, null)
        }
        startActivity(intent)
    }
}
