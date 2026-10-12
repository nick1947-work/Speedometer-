package com.creationunicorn.speedometer.ui.onboarding

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.viewpager2.widget.ViewPager2
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ActivityOnboardingBinding
import com.creationunicorn.speedometer.ui.MainActivity

class OnboardingActivity : AppCompatActivity() {

    private lateinit var binding: ActivityOnboardingBinding
    private lateinit var adapter: OnboardingAdapter

    private val slides = listOf(
        OnboardingSlide(
            titleRes = R.string.onboarding_title_1,
            descRes = R.string.onboarding_desc_1,
            illustrationRes = R.drawable.img_onboarding_gauge
        ),
        OnboardingSlide(
            titleRes = R.string.onboarding_title_2,
            descRes = R.string.onboarding_desc_2,
            illustrationRes = R.drawable.img_onboarding_route
        ),
        OnboardingSlide(
            titleRes = R.string.onboarding_title_3,
            descRes = R.string.onboarding_desc_3,
            illustrationRes = R.drawable.img_onboarding_alert
        )
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOnboardingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = OnboardingAdapter(slides)
        binding.onboardingViewPager.adapter = adapter

        setupDots(0)

        binding.onboardingViewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                setupDots(position)
            }
        })

        binding.btnContinue.setOnClickListener {
            val current = binding.onboardingViewPager.currentItem
            if (current < slides.size - 1) {
                binding.onboardingViewPager.currentItem = current + 1
            } else {
                completeOnboarding()
            }
        }
    }

    private fun setupDots(activePosition: Int) {
        val dots = listOf(binding.dot1, binding.dot2, binding.dot3)
        for (i in dots.indices) {
            val dot = dots[i]
            val params = dot.layoutParams as ViewGroup.MarginLayoutParams
            if (i == activePosition) {
                dot.background = ContextCompat.getDrawable(this, R.drawable.bg_dot_active)
                params.width = (18 * resources.displayMetrics.density).toInt()
            } else {
                dot.background = ContextCompat.getDrawable(this, R.drawable.bg_dot_inactive)
                params.width = (8 * resources.displayMetrics.density).toInt()
            }
            dot.layoutParams = params
        }
    }

    private fun completeOnboarding() {
        val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
        prefs.edit().putBoolean("onboarding_completed", true).apply()

        startActivity(Intent(this, MainActivity::class.java))
        finish()
    }
}
