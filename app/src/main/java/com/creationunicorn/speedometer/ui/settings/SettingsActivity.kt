package com.creationunicorn.speedometer.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackSettings.setOnClickListener {
            finish()
        }

        binding.btnUnitKm.setOnClickListener {
            binding.btnUnitKm.setBackgroundResource(com.creationunicorn.speedometer.R.drawable.bg_button_continue)
            binding.btnUnitKm.setTextColor(android.graphics.Color.BLACK)
            binding.btnUnitMph.background = null
            binding.btnUnitMph.setTextColor(android.graphics.Color.GRAY)
            binding.btnUnitKnot.background = null
            binding.btnUnitKnot.setTextColor(android.graphics.Color.GRAY)
        }

        binding.btnUnitMph.setOnClickListener {
            binding.btnUnitMph.setBackgroundResource(com.creationunicorn.speedometer.R.drawable.bg_button_continue)
            binding.btnUnitMph.setTextColor(android.graphics.Color.BLACK)
            binding.btnUnitKm.background = null
            binding.btnUnitKm.setTextColor(android.graphics.Color.GRAY)
            binding.btnUnitKnot.background = null
            binding.btnUnitKnot.setTextColor(android.graphics.Color.GRAY)
        }

        binding.btnUnitKnot.setOnClickListener {
            binding.btnUnitKnot.setBackgroundResource(com.creationunicorn.speedometer.R.drawable.bg_button_continue)
            binding.btnUnitKnot.setTextColor(android.graphics.Color.BLACK)
            binding.btnUnitKm.background = null
            binding.btnUnitKm.setTextColor(android.graphics.Color.GRAY)
            binding.btnUnitMph.background = null
            binding.btnUnitMph.setTextColor(android.graphics.Color.GRAY)
        }
    }
}
