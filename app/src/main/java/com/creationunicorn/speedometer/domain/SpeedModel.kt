package com.creationunicorn.speedometer.domain

import java.util.Locale

enum class SpeedUnit(val displayName: String, val distanceDisplayName: String, val multiplierFromMps: Double) {
    KMH("km/h", "km", 3.6),
    MPH("mph", "mi", 2.23694),
    KNOTS("knot", "nm", 1.94384);

    fun convert(speedMps: Float): Double = speedMps * multiplierFromMps
    fun convertDistance(meters: Double): Double = when (this) {
        KMH -> meters / 1000.0
        MPH -> meters / 1609.34
        KNOTS -> meters / 1852.0
    }
    fun formatDistance(meters: Double): String {
        return when (this) {
            KMH -> String.format(Locale.US, "%.2f km", convertDistance(meters))
            MPH -> String.format(Locale.US, "%.2f mi", convertDistance(meters))
            KNOTS -> String.format(Locale.US, "%.2f nm", convertDistance(meters))
        }
    }
}

data class SpeedMetrics(
    val currentSpeedMps: Float = 0f,
    val maxSpeedMps: Float = 0f,
    val averageSpeedMps: Float = 0f,
    val totalDistanceMeters: Double = 0.0,
    val elapsedTimeMillis: Long = 0L,
    val isGpsActive: Boolean = false,
    val accuracyMeters: Float = 0f,
    val speedLimitMps: Float = 22.22f, // Default ~80 km/h
    val latitude: Double = 28.5967,
    val longitude: Double = 77.3840
) {
    fun isOverSpeedLimit(): Boolean = currentSpeedMps > speedLimitMps && speedLimitMps > 0
}
