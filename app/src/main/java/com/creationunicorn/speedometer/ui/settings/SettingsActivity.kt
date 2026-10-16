package com.creationunicorn.speedometer.ui.settings

import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.GradientDrawable
import android.net.Uri
import android.os.Bundle
import android.view.ViewGroup
import android.view.Window
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ActivitySettingsBinding
import com.creationunicorn.speedometer.service.SpeedometerNotificationManager

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
        val currentUnit = prefs.getString("speed_unit", "km") ?: "km"
        updateUnitUi(currentUnit)

        binding.btnUnitKm.setOnClickListener {
            prefs.edit().putString("speed_unit", "km").apply()
            updateUnitUi("km")
            updateDisplayValues(prefs)
            Toast.makeText(this, "Speed unit set to km/h", Toast.LENGTH_SHORT).show()
        }

        binding.btnUnitMph.setOnClickListener {
            prefs.edit().putString("speed_unit", "mph").apply()
            updateUnitUi("mph")
            updateDisplayValues(prefs)
            Toast.makeText(this, "Speed unit set to mph", Toast.LENGTH_SHORT).show()
        }

        binding.btnUnitKnot.setOnClickListener {
            prefs.edit().putString("speed_unit", "knot").apply()
            updateUnitUi("knot")
            updateDisplayValues(prefs)
            Toast.makeText(this, "Speed unit set to knots", Toast.LENGTH_SHORT).show()
        }

        // Vehicle Modes
        val currentVehicle = prefs.getInt("setting_vehicle_mode", 3)
        updateVehicleModeUi(currentVehicle)

        binding.btnSettingWalk.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 0).apply()
            updateVehicleModeUi(0)
            updateDisplayValues(prefs)
            Toast.makeText(this, "Default mode: Walk", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingBike.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 1).apply()
            updateVehicleModeUi(1)
            updateDisplayValues(prefs)
            Toast.makeText(this, "Default mode: Bike", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingMoto.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 2).apply()
            updateVehicleModeUi(2)
            updateDisplayValues(prefs)
            Toast.makeText(this, "Default mode: Motorcycle", Toast.LENGTH_SHORT).show()
        }
        binding.btnSettingCar.setOnClickListener {
            prefs.edit().putInt("setting_vehicle_mode", 3).apply()
            updateVehicleModeUi(3)
            updateDisplayValues(prefs)
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

        // Update display values for Max Mileage and Warning When Over
        updateDisplayValues(prefs)

        // Custom Dialog: Maximum Mileage
        binding.rowMaxMileage.setOnClickListener {
            showMaximumMileageDialog(prefs)
        }

        // Custom Dialog: Warning when over
        binding.rowWarningSpeedLimit.setOnClickListener {
            showWarningWhenOverDialog(prefs)
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

        // Notification Bar Switch: controls ongoing 4-button quick bar
        binding.switchNotification.isChecked = prefs.getBoolean("pref_notification", true)
        binding.switchNotification.setOnCheckedChangeListener { _, isChecked ->
            prefs.edit().putBoolean("pref_notification", isChecked).apply()
            if (isChecked) {
                SpeedometerNotificationManager.showQuickBarNotification(this)
            } else {
                SpeedometerNotificationManager.hideQuickBarNotification(this)
            }
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

        // Language row
        binding.rowLanguage.setOnClickListener {
            val languages = arrayOf("English", "Spanish", "French", "German", "Japanese", "Chinese")
            AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
                .setTitle("Select Language")
                .setItems(languages) { _, which ->
                    binding.tvLanguageVal.text = "${languages[which]} >"
                    Toast.makeText(this, "Language: ${languages[which]}", Toast.LENGTH_SHORT).show()
                }
                .setNegativeButton("Cancel", null)
                .show()
        }

        // Permission settings row -> launches PermissionSettingsActivity
        binding.rowPermissionSettings.setOnClickListener {
            startActivity(Intent(this, PermissionSettingsActivity::class.java))
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

    private fun updateDisplayValues(prefs: android.content.SharedPreferences) {
        val currentUnit = prefs.getString("speed_unit", "km") ?: "km"
        val distUnit = when (currentUnit) {
            "mph" -> "mi"
            "knot" -> "nm"
            else -> "km"
        }
        val speedUnit = when (currentUnit) {
            "mph" -> "mph"
            "knot" -> "knots"
            else -> "km/h"
        }

        val maxMileage = prefs.getFloat("pref_max_mileage", 200f)
        binding.tvMaxMileageVal.text = "${maxMileage.toInt()} $distUnit >"

        val warningLimit = prefs.getFloat("pref_speed_warning_limit", 100f)
        binding.tvWarningSpeedLimitVal.text = "${warningLimit.toInt()} $speedUnit >"
    }

    private fun getVehicleModeName(mode: Int): String {
        return when (mode) {
            0 -> "Walk"
            1 -> "Bike"
            2 -> "Motorcycle"
            else -> "Car"
        }
    }

    /**
     * Shows custom "Warning when over" Dialog matching user screenshot 1.
     */
    private fun showWarningWhenOverDialog(prefs: android.content.SharedPreferences) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_warning_when_over)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val vehicleMode = prefs.getInt("setting_vehicle_mode", 3)
        val vehicleName = getVehicleModeName(vehicleMode)

        val currentUnit = prefs.getString("speed_unit", "km") ?: "km"
        val speedUnit = when (currentUnit) {
            "mph" -> "mph"
            "knot" -> "knots"
            else -> "km/h"
        }

        val themeColorHex = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
        val themeColor = try { Color.parseColor(themeColorHex) } catch (e: Exception) { Color.parseColor("#00FFF2") }

        val tvSubtitle = dialog.findViewById<TextView>(R.id.tvSubtitle)
        val etSpeedLimit = dialog.findViewById<EditText>(R.id.etSpeedLimit)
        val tvUnit = dialog.findViewById<TextView>(R.id.tvUnit)
        val btnClose = dialog.findViewById<ImageView>(R.id.btnClose)
        val btnDone = dialog.findViewById<TextView>(R.id.btnDone)

        val btnDriving = dialog.findViewById<ViewGroup>(R.id.btnPresetDriving)
        val btnMoto = dialog.findViewById<ViewGroup>(R.id.btnPresetMoto)
        val btnCycling = dialog.findViewById<ViewGroup>(R.id.btnPresetCycling)
        val btnWalking = dialog.findViewById<ViewGroup>(R.id.btnPresetWalking)

        val tvDriving = dialog.findViewById<TextView>(R.id.tvPresetDrivingVal)
        val tvMoto = dialog.findViewById<TextView>(R.id.tvPresetMotoVal)
        val tvCycling = dialog.findViewById<TextView>(R.id.tvPresetCyclingVal)
        val tvWalking = dialog.findViewById<TextView>(R.id.tvPresetWalkingVal)

        tvSubtitle.text = "Current selection: $vehicleName"
        tvUnit.text = speedUnit

        val curLimit = prefs.getFloat("pref_speed_warning_limit", 100f).toInt()
        etSpeedLimit.setText(curLimit.toString())

        // Values based on unit
        val (valDriving, valMoto, valCycling, valWalking) = when (currentUnit) {
            "mph" -> listOf(65, 45, 15, 4)
            "knot" -> listOf(55, 35, 12, 3)
            else -> listOf(100, 70, 25, 6)
        }

        tvDriving.text = "$valDriving $speedUnit"
        tvMoto.text = "$valMoto $speedUnit"
        tvCycling.text = "$valCycling $speedUnit"
        tvWalking.text = "$valWalking $speedUnit"

        val presets = listOf(
            btnDriving to valDriving,
            btnMoto to valMoto,
            btnCycling to valCycling,
            btnWalking to valWalking
        )

        fun updatePresetSelection(selectedValue: Int) {
            presets.forEach { (view, value) ->
                view.isSelected = (value == selectedValue)
            }
        }
        updatePresetSelection(curLimit)

        presets.forEach { (view, value) ->
            view.setOnClickListener {
                etSpeedLimit.setText(value.toString())
                updatePresetSelection(value)
            }
        }

        // Apply theme color to Done button
        val doneBg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(themeColor)
        }
        btnDone.background = doneBg

        btnClose.setOnClickListener {
            dialog.dismiss()
        }

        btnDone.setOnClickListener {
            val inputVal = etSpeedLimit.text.toString().toFloatOrNull() ?: 100f
            prefs.edit().putFloat("pref_speed_warning_limit", inputVal).apply()
            updateDisplayValues(prefs)
            Toast.makeText(this, "Speed warning: ${inputVal.toInt()} $speedUnit", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
    }

    /**
     * Shows custom "Maximum mileage" Dialog matching user screenshot 4.
     */
    private fun showMaximumMileageDialog(prefs: android.content.SharedPreferences) {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.dialog_maximum_mileage)
        dialog.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window?.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )

        val vehicleMode = prefs.getInt("setting_vehicle_mode", 3)
        val vehicleName = getVehicleModeName(vehicleMode)

        val currentUnit = prefs.getString("speed_unit", "km") ?: "km"
        val distUnit = when (currentUnit) {
            "mph" -> "mi"
            "knot" -> "nm"
            else -> "km"
        }

        val themeColorHex = prefs.getString("pref_theme_color", "#00FFF2") ?: "#00FFF2"
        val themeColor = try { Color.parseColor(themeColorHex) } catch (e: Exception) { Color.parseColor("#00FFF2") }

        val tvTrafficSelection = dialog.findViewById<TextView>(R.id.tvTrafficSelection)
        val btnOption1 = dialog.findViewById<ViewGroup>(R.id.btnOption1)
        val btnOption2 = dialog.findViewById<ViewGroup>(R.id.btnOption2)
        val btnOption3 = dialog.findViewById<ViewGroup>(R.id.btnOption3)
        val tvOption1 = dialog.findViewById<TextView>(R.id.tvOption1)
        val tvOption2 = dialog.findViewById<TextView>(R.id.tvOption2)
        val tvOption3 = dialog.findViewById<TextView>(R.id.tvOption3)
        val btnDone = dialog.findViewById<TextView>(R.id.btnDone)

        tvTrafficSelection.text = "Current selection: $vehicleName"

        // Scale mileage options based on vehicle mode and unit
        val (opt1, opt2, opt3) = when (currentUnit) {
            "knot" -> listOf(15, 30, 50)
            "mph" -> listOf(30, 60, 100)
            else -> listOf(50, 100, 200)
        }

        tvOption1.text = "$opt1 $distUnit"
        tvOption2.text = "$opt2 $distUnit"
        tvOption3.text = "$opt3 $distUnit"

        var selectedMileage = prefs.getFloat("pref_max_mileage", opt3.toFloat()).toInt()
        if (selectedMileage !in listOf(opt1, opt2, opt3)) {
            selectedMileage = opt3
        }

        fun updateOptions(selected: Int) {
            btnOption1.isSelected = (selected == opt1)
            btnOption2.isSelected = (selected == opt2)
            btnOption3.isSelected = (selected == opt3)
        }
        updateOptions(selectedMileage)

        btnOption1.setOnClickListener {
            selectedMileage = opt1
            updateOptions(selectedMileage)
        }
        btnOption2.setOnClickListener {
            selectedMileage = opt2
            updateOptions(selectedMileage)
        }
        btnOption3.setOnClickListener {
            selectedMileage = opt3
            updateOptions(selectedMileage)
        }

        // Apply theme color to Done button
        val doneBg = GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            cornerRadius = 24f
            setColor(themeColor)
        }
        btnDone.background = doneBg

        btnDone.setOnClickListener {
            prefs.edit().putFloat("pref_max_mileage", selectedMileage.toFloat()).apply()
            updateDisplayValues(prefs)
            Toast.makeText(this, "Max mileage: $selectedMileage $distUnit", Toast.LENGTH_SHORT).show()
            dialog.dismiss()
        }

        dialog.show()
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
