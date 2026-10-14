package com.creationunicorn.speedometer.ui.settings

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)

        // Back
        binding.ivBackSettings.setOnClickListener {
            finish()
        }

        // Speed Unit setup
        val currentUnit = prefs.getString("speed_unit", "km")
        updateUnitUi(currentUnit ?: "km")

        binding.btnUnitKm.setOnClickListener {
            prefs.edit().putString("speed_unit", "km").apply()
            updateUnitUi("km")
            Toast.makeText(this, "Speed unit set to km/h", Toast.LENGTH_SHORT).show()
        }

        binding.btnUnitMph.setOnClickListener {
            prefs.edit().putString("speed_unit", "mph").apply()
            updateUnitUi("mph")
            Toast.makeText(this, "Speed unit set to mph", Toast.LENGTH_SHORT).show()
        }

        binding.btnUnitKnot.setOnClickListener {
            prefs.edit().putString("speed_unit", "knot").apply()
            updateUnitUi("knot")
            Toast.makeText(this, "Speed unit set to knots", Toast.LENGTH_SHORT).show()
        }

        // Vehicle Modes
        val currentVehicle = prefs.getInt("setting_vehicle_mode", 3)
        updateVehicleModeUi(currentVehicle)

        binding.btnSettingWalk.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 0).apply()
            updateVehicleModeUi(0)
            Toast.makeText(this, "Default mode: Walk", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingBike.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 1).apply()
            updateVehicleModeUi(1)
            Toast.makeText(this, "Default mode: Bike", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingMoto.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 2).apply()
            updateVehicleModeUi(2)
            Toast.makeText(this, "Default mode: Motorcycle", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingCar.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 3).apply()
            updateVehicleModeUi(3)
            Toast.makeText(this, "Default mode: Car", Toast.LENGTH_SHORT).show()
        }

        // Total Mileage
        val mileage = prefs.getFloat("total_mileage_km", 0f)
        binding.tvSettingMileageVal.text = String.format("%.1f km", mileage)

        binding.btnResetMileage.setOnClickListener {
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Reset Mileage")
                .setMessage("Are you sure you want to reset total mileage to 0 km?")
                .setPositiveButton("Reset") { _, _ ->
                    prefs.edit().putFloat("total_mileage_km", 0f).apply()
                    binding.tvSettingMileageVal.text = "0.0 km"
                    Toast.makeText(this, "Mileage reset to 0 km", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Switches
        binding.switchSpeedWarning.isChecked = prefs.getBoolean("pref_speed_warning", true)
        binding.switchSpeedWarning.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_speed_warning", isChecked).apply()
            Toast.makeText(this, "Speed Warning: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.switchAnimation.isChecked = prefs.getBoolean("pref_animation", true)
        binding.switchAnimation.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_animation", isChecked).apply()
        }

        binding.switchSound.isChecked = prefs.getBoolean("pref_sound", true)
        binding.switchSound.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_sound", isChecked).apply()
            Toast.makeText(this, "Sound Effects: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.switchVibration.isChecked = prefs.getBoolean("pref_vibration", true)
        binding.switchVibration.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_vibration", isChecked).apply()
            Toast.makeText(this, "Vibration: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        binding.switchRouteTracking.isChecked = prefs.getBoolean("pref_route_tracking", true)
        binding.switchRouteTracking.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_route_tracking", isChecked).apply()
        }

        binding.switchNotification.isChecked = prefs.getBoolean("pref_notification", true)
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_notification", isChecked).apply()
        }

        binding.switchKeepScreenOn.isChecked = prefs.getBoolean("pref_keep_screen_on", true)
        binding.switchKeepScreenOn.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_keep_screen_on", isChecked).apply()
            Toast.makeText(this, "Keep Screen On: ${if (isChecked) "Enabled" else "Disabled"}", Toast.LENGTH_SHORT).show()
        }

        binding.switchWindowMode.isChecked = prefs.getBoolean("pref_window_mode", false)
        binding.switchWindowMode.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_window_mode", isChecked).apply()
            Toast.makeText(this, "Window / HUD Mode: ${if (isChecked) "ON" else "OFF"}", Toast.LENGTH_SHORT).show()
        }

        // Theme Color Swatches (8 colors)
        val swatches = listOf(
            Triple(binding.swatchCyan, "#00FFF2", "Cyan"),
            Triple(binding.swatchSkyBlue, "#29B6F6", "Sky Blue"),
            Triple(binding.swatchGreen, "#76FF03", "Green"),
            Triple(binding.swatchYellow, "#FFEA00", "Yellow"),
            Triple(binding.swatchOrange, "#FF6D00", "Orange"),
            Triple(binding.swatchRed, "#FF1744", "Red"),
            Triple(binding.swatchPurple, "#B388FF", "Purple"),
            Triple(binding.swatchWhite, "#FFFFFF", "White")
        )
        val currentThemeColor = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
        updateThemeSwatchesUi(swatches, currentThemeColor)

        for ((view, hex, name) in swatches) {
            view.setOnClickListener {
                prefs.edit().putString("pref_theme_color", hex).apply()
                updateThemeSwatchesUi(swatches, hex)
                Toast.makeText(this, "Theme color: $name", Toast.LENGTH_SHORT).show()
            }
        }

        // Support Us actions
        binding.btnRateUs.setOnClickListener {
            try {
                val rateIntent = Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$packageName"))
                startActivity(rateIntent)
            } catch (e: Exception) {
                Toast.makeText(this, "Thank you for rating Speedometer 5 stars! ★★★★★", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnFeedback.setOnClickListener {
            val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                data = Uri.parse("mailto:support@speedometerapp.com")
                putExtra(Intent.EXTRA_SUBJECT, "Speedometer App Feedback")
            }
            try {
                startActivity(Intent.createChooser(emailIntent, "Send Feedback"))
            } catch (e: Exception) {
                Toast.makeText(this, "Feedback: support@speedometerapp.com", Toast.LENGTH_LONG).show()
            }
        }

        binding.btnShare.setOnClickListener {
            val shareIntent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_SUBJECT, "GPS Speedometer")
                putExtra(Intent.EXTRA_TEXT, "Check out this amazing GPS Speedometer app: https://play.google.com/store/apps/details?id=$packageName")
            }
            startActivity(Intent.createChooser(shareIntent, "Share with friends"))
        }

        binding.btnPrivacy.setOnClickListener {
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Privacy Policy")
                .setMessage("GPS Speedometer respects your privacy. All location, speed, and trip data is stored locally on your device and is never uploaded or shared with third parties without your explicit permission.")
                .setPositiveButton("OK", null)
                .show()
        }
    }

    private fun updateUnitUi(unit: String) {
        val selectedBg = R.drawable.bg_button_continue
        val activeColor = Color.BLACK
        val inactiveColor = Color.GRAY

        when (unit) {
            "mph" -> {
                binding.btnUnitMph.setBackgroundResource(selectedBg)
                binding.btnUnitMph.setTextColor(activeColor)
                binding.btnUnitKm.background = null
                binding.btnUnitKm.setTextColor(inactiveColor)
                binding.btnUnitKnot.background = null
                binding.btnUnitKnot.setTextColor(inactiveColor)
            }
            "knot" -> {
                binding.btnUnitKnot.setBackgroundResource(selectedBg)
                binding.btnUnitKnot.setTextColor(activeColor)
                binding.btnUnitKm.background = null
                binding.btnUnitKm.setTextColor(inactiveColor)
                binding.btnUnitMph.background = null
                binding.btnUnitMph.setTextColor(inactiveColor)
            }
            else -> {
                binding.btnUnitKm.setBackgroundResource(selectedBg)
                binding.btnUnitKm.setTextColor(activeColor)
                binding.btnUnitMph.background = null
                binding.btnUnitMph.setTextColor(inactiveColor)
                binding.btnUnitKnot.background = null
                binding.btnUnitKnot.setTextColor(inactiveColor)
            }
        }
    }

    private fun updateVehicleModeUi(mode: Int) {
        val selectedBg = R.drawable.bg_button_continue
        val activeColor = Color.BLACK
        val inactiveColor = Color.GRAY

        val buttons = listOf(
            binding.btnSettingWalk,
            binding.btnSettingBike,
            binding.btnSettingMoto,
            binding.btnSettingCar
        )

        for (i in buttons.indices) {
            if (i == mode) {
                buttons[i].setBackgroundResource(selectedBg)
                buttons[i].setTextColor(activeColor)
            } else {
                buttons[i].background = null
                buttons[i].setTextColor(inactiveColor)
            }
        }
    }

    private fun updateThemeSwatchesUi(
        swatches: List<Triple<android.view.View, String, String>>,
        selectedHex: String
    ) {
        for ((view, hex, _) in swatches) {
            if (hex.equals(selectedHex, ignoreCase = true)) {
                view.scaleX = 1.15f
                view.scaleY = 1.15f
                view.alpha = 1.0f
                view.elevation = 8f
            } else {
                view.scaleX = 0.95f
                view.scaleY = 0.95f
                view.alpha = 0.7f
                view.elevation = 0f
            }
        }
    }
}
