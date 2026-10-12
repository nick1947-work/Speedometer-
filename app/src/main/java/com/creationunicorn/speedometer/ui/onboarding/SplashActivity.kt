package com.creationunicorn.speedometer.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.databinding.ActivitySplashBinding
import com.creationunicorn.speedometer.ui.MainActivity

class SplashActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Check if user has already completed onboarding
        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        val isOnboardingCompleted = prefs.getBoolean("onboarding_completed", false)

        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnboardingCompleted) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, OnboardingActivity::class.java))
            }
            finish()
        }, 2200) // 2.2 second smooth splash intro
    }
}
