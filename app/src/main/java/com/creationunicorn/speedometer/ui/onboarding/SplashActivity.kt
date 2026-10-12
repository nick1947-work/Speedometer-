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

        // Animate needle rotation: 90 -> 250 -> 130 degrees
        android.animation.ValueAnimator.ofFloat(90f, 250f, 130f).apply {
            duration = 1800
            interpolator = android.view.animation.DecelerateInterpolator()
            addUpdateListener { animator ->
                binding.pointerView.rotation = animator.animatedValue as Float
            }
            start()
        }

        // Animate progress bar 0 to 100
        android.animation.ValueAnimator.ofInt(0, 100).apply {
            duration = 2000
            interpolator = android.view.animation.LinearInterpolator()
            addUpdateListener { animator ->
                binding.splashProgressBar.progress = animator.animatedValue as Int
            }
            start()
        }

        Handler(Looper.getMainLooper()).postDelayed({
            if (isOnboardingCompleted) {
                startActivity(Intent(this, MainActivity::class.java))
            } else {
                startActivity(Intent(this, OnboardingActivity::class.java))
            }
            finish()
        }, 2200)
    }
}
