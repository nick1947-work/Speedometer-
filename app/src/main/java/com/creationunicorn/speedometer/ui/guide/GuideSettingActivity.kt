package com.creationunicorn.speedometer.ui.guide

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ActivityGuideSettingBinding

class GuideSettingActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TYPE = "guide_type"
        const val TYPE_BATTERY = 1
        const val TYPE_AUTOSTART = 2
    }

    private lateinit var binding: ActivityGuideSettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityGuideSettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val guideType = intent.getIntExtra(EXTRA_TYPE, TYPE_BATTERY)

        if (guideType == TYPE_BATTERY) {
            binding.tvStepNumber.text = "1"
            binding.tvGuideTitle.text = getString(R.string.guide_battery_title)
            binding.tvMockupCategory.text = "BACKGROUND SETTINGS"
            binding.tvHighlightOptionTitle.text = "No restrictions"
            binding.tvHighlightOptionDesc.text = "Battery saver doesn't restrict app's activity"
            binding.ivHighlightCheck.visibility = View.VISIBLE
            binding.switchMockup.visibility = View.GONE
            binding.secondOptionLayout.visibility = View.VISIBLE
        } else {
            binding.tvStepNumber.text = "1"
            binding.tvGuideTitle.text = getString(R.string.guide_autostart_title)
            binding.tvMockupCategory.text = "Background autostart"
            binding.tvHighlightOptionTitle.text = "Speedometer"
            binding.tvHighlightOptionDesc.text = "1 app can start in the background"
            binding.ivHighlightCheck.visibility = View.GONE
            binding.switchMockup.visibility = View.VISIBLE
            binding.switchMockup.isChecked = true
            binding.secondOptionLayout.visibility = View.GONE
        }

        binding.ivCloseGuide.setOnClickListener {
            finish()
        }

        binding.btnGoToSet.setOnClickListener {
            try {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            } catch (e: Exception) {
                val intent = Intent(Settings.ACTION_SETTINGS)
                startActivity(intent)
            }
            finish()
        }
    }
}
