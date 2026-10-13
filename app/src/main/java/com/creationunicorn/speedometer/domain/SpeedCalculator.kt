package com.creationunicorn.speedometer.domain

import android.location.Location

class SpeedCalculator {
    private var lastLocation: Location? = null
    private var maxSpeedMps: Float = 0f
    private var totalDistanceMeters: Double = 0.0
    private var speedSumMps: Double = 0.0
    private var sampleCount: Long = 0L

    fun processLocation(location: Location, speedLimitMps: Float = 22.22f): SpeedMetrics {
        // Filter out unreliable fixes (> 25m accuracy)
        if (location.accuracy > 25.0f && location.accuracy != 0.0f) {
            return SpeedMetrics(
                currentSpeedMps = 0f,
                maxSpeedMps = maxSpeedMps,
                averageSpeedMps = if (sampleCount > 0) (speedSumMps / sampleCount).toFloat() else 0f,
                totalDistanceMeters = totalDistanceMeters,
                isGpsActive = true,
                accuracyMeters = location.accuracy,
                speedLimitMps = speedLimitMps,
                latitude = location.latitude,
                longitude = location.longitude
            )
        }

        var speed = if (location.hasSpeed()) location.speed else 0f

        val prev = lastLocation
        if (prev != null) {
            val dist = prev.distanceTo(location)
            val timeSec = (location.time - prev.time) / 1000.0

            if (!location.hasSpeed() && timeSec > 0) {
                speed = (dist / timeSec).toFloat()
            }

            // Reject static drift noise: distance only recorded if above ~1.5 km/h
            if (speed > 0.45f) {
                totalDistanceMeters += dist
            }
        }

        // Clamp stop jitter
        if (speed < 0.4f) {
            speed = 0f
        }

        if (speed > maxSpeedMps) {
            maxSpeedMps = speed
        }

        if (speed > 0f) {
            speedSumMps += speed
            sampleCount++
        }

        lastLocation = location

        val avgSpeed = if (sampleCount > 0) (speedSumMps / sampleCount).toFloat() else 0f

        return SpeedMetrics(
            currentSpeedMps = speed,
            maxSpeedMps = maxSpeedMps,
            averageSpeedMps = avgSpeed,
            totalDistanceMeters = totalDistanceMeters,
            isGpsActive = true,
            accuracyMeters = location.accuracy,
            speedLimitMps = speedLimitMps,
            latitude = location.latitude,
            longitude = location.longitude
        )
    }

    fun reset() {
        lastLocation = null
        maxSpeedMps = 0f
        totalDistanceMeters = 0.0
        speedSumMps = 0.0
        sampleCount = 0L
    }
}
