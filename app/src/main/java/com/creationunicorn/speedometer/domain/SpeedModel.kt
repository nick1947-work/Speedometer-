package com.creationunicorn.speedometer.domain

enum class SpeedUnit(val displayName: String, val multiplierFromMps: Double) {
    KMH("km/h", 3.6),
    MPH("mph", 2.23694),
    KNOTS("knots", 1.94384);

    fun convert(speedMps: Float): Double = speedMps * multiplierFromMps
    fun formatDistance(meters: Double): String {
        return when (this) {
            KMH -> String.format("%.2f km", meters / 1000.0)
            MPH -> String.format("%.2f mi", meters / 1609.34)
            KNOTS -> String.format("%.2f nm", meters / 1852.0)
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
    val speedLimitMps: Float = 22.22f // Default ~80 km/h
) {
    fun isOverSpeedLimit(): Boolean = currentSpeedMps > speedLimitMps && speedLimitMps > 0
}
