package com.creationunicorn.speedometer.data

import org.json.JSONObject

data class TripRecord(
    val id: String,
    var title: String,
    val vehicleMode: Int, // 0 = Walk, 1 = Bicycle, 2 = Motorcycle, 3 = Car
    val distance: String,
    val distanceUnit: String,
    val avgSpeed: String,
    val maxSpeed: String,
    val speedUnit: String,
    val durationStr: String,
    val durationMillis: Long,
    val dateStr: String,
    val fromTime: String,
    val toTime: String,
    val fromAddress: String,
    val toAddress: String,
    val latitude: Double = 28.5967,
    val longitude: Double = 77.3840
) {
    fun toJson(): JSONObject {
        val json = JSONObject()
        json.put("id", id)
        json.put("title", title)
        json.put("vehicleMode", vehicleMode)
        json.put("distance", distance)
        json.put("distanceUnit", distanceUnit)
        json.put("avgSpeed", avgSpeed)
        json.put("maxSpeed", maxSpeed)
        json.put("speedUnit", speedUnit)
        json.put("durationStr", durationStr)
        json.put("durationMillis", durationMillis)
        json.put("dateStr", dateStr)
        json.put("fromTime", fromTime)
        json.put("toTime", toTime)
        json.put("fromAddress", fromAddress)
        json.put("toAddress", toAddress)
        json.put("latitude", latitude)
        json.put("longitude", longitude)
        return json
    }

    companion object {
        fun fromJson(json: JSONObject): TripRecord {
            return TripRecord(
                id = json.optString("id", System.currentTimeMillis().toString()),
                title = json.optString("title", "Trip"),
                vehicleMode = json.optInt("vehicleMode", 0),
                distance = json.optString("distance", "0"),
                distanceUnit = json.optString("distanceUnit", "nm"),
                avgSpeed = json.optString("avgSpeed", "0"),
                maxSpeed = json.optString("maxSpeed", "0"),
                speedUnit = json.optString("speedUnit", "knot"),
                durationStr = json.optString("durationStr", "00:00:00"),
                durationMillis = json.optLong("durationMillis", 0L),
                dateStr = json.optString("dateStr", "Oct 14, 2026"),
                fromTime = json.optString("fromTime", "00:00"),
                toTime = json.optString("toTime", "00:00"),
                fromAddress = json.optString("fromAddress", "--"),
                toAddress = json.optString("toAddress", "--"),
                latitude = json.optDouble("latitude", 28.5967),
                longitude = json.optDouble("longitude", 77.3840)
            )
        }
    }
}
