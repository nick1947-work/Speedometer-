package com.creationunicorn.speedometer.ui.trip

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.databinding.ActivityTripSummaryBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TripSummaryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_DISTANCE = "trip_distance"
        const val EXTRA_DURATION_MILLIS = "trip_duration_millis"
        const val EXTRA_AVG_SPEED = "trip_avg_speed"
        const val EXTRA_MAX_SPEED = "trip_max_speed"
        const val EXTRA_MODE = "trip_mode"
    }

    private lateinit var binding: ActivityTripSummaryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTripSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val distanceText = intent.getStringExtra(EXTRA_DISTANCE) ?: "0 km"
        val durationMillis = intent.getLongExtra(EXTRA_DURATION_MILLIS, 0L)
        val avgSpeedText = intent.getStringExtra(EXTRA_AVG_SPEED) ?: "0 km/h"
        val maxSpeedText = intent.getStringExtra(EXTRA_MAX_SPEED) ?: "0 km/h"

        val totalSec = durationMillis / 1000
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        val durationFormatted = String.format("%02d:%02d:%02d", h, m, s)

        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault()).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date())

        binding.tvTripDate.text = currentDate
        binding.tvFromTime.text = currentTime
        binding.tvToTime.text = currentTime

        binding.tvSummaryDistance.text = distanceText
        binding.tvSummaryDuration.text = durationFormatted
        binding.tvSummaryAvgSpeed.text = avgSpeedText
        binding.tvSummaryMaxSpeed.text = maxSpeedText

        binding.ivBackTrip.setOnClickListener {
            finish()
        }
    }
}
