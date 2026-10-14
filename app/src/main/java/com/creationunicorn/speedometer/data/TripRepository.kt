package com.creationunicorn.speedometer.data

import android.content.Context
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TripRepository {

    private const val PREFS_NAME = "speedometer_trips_store"
    private const val KEY_TRIPS = "key_stored_trips"
    private const val KEY_IS_SEEDED = "key_is_trips_seeded"

    fun getAllTrips(context: Context): MutableList<TripRecord> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        if (!prefs.getBoolean(KEY_IS_SEEDED, false)) {
            val initial = getSeedTrips()
            saveAllTrips(context, initial)
            prefs.edit().putBoolean(KEY_IS_SEEDED, true).apply()
            return initial
        }

        val jsonStr = prefs.getString(KEY_TRIPS, null) ?: return mutableListOf()
        val list = mutableListOf<TripRecord>()
        try {
            val arr = JSONArray(jsonStr)
            for (i in 0 until arr.length()) {
                list.add(TripRecord.fromJson(arr.getJSONObject(i)))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return list
    }

    fun getTripsByMode(context: Context, mode: Int): List<TripRecord> {
        return getAllTrips(context).filter { it.vehicleMode == mode }
    }

    fun saveTrip(context: Context, trip: TripRecord) {
        val current = getAllTrips(context)
        current.add(0, trip) // Add to top
        saveAllTrips(context, current)
    }

    fun updateTripTitle(context: Context, tripId: String, newTitle: String) {
        val current = getAllTrips(context)
        val item = current.find { it.id == tripId }
        if (item != null) {
            item.title = newTitle
            saveAllTrips(context, current)
        }
    }

    fun deleteTrip(context: Context, tripId: String) {
        val current = getAllTrips(context)
        current.removeAll { it.id == tripId }
        saveAllTrips(context, current)
    }

    fun deleteTrips(context: Context, tripIds: Set<String>) {
        val current = getAllTrips(context)
        current.removeAll { it.id in tripIds }
        saveAllTrips(context, current)
    }

    fun getNextTripNumber(context: Context): Int {
        val all = getAllTrips(context)
        return all.size + 1
    }

    private fun saveAllTrips(context: Context, trips: List<TripRecord>) {
        val arr = JSONArray()
        for (trip in trips) {
            arr.put(trip.toJson())
        }
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        prefs.edit().putString(KEY_TRIPS, arr.toString()).apply()
    }

    private fun getSeedTrips(): MutableList<TripRecord> {
        return mutableListOf(
            TripRecord(
                id = "mock_3",
                title = "Trip-3",
                vehicleMode = 0, // Walk
                distance = "0",
                distanceUnit = "nm",
                avgSpeed = "0",
                maxSpeed = "0",
                speedUnit = "knot",
                durationStr = "00:00:06",
                durationMillis = 6000L,
                dateStr = "Oct 14, 2026",
                fromTime = "01:54",
                toTime = "01:54",
                fromAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
                toAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
                latitude = 28.5967,
                longitude = 77.3840
            ),
            TripRecord(
                id = "mock_2",
                title = "Trip-2",
                vehicleMode = 0, // Walk
                distance = "0",
                distanceUnit = "nm",
                avgSpeed = "0",
                maxSpeed = "0",
                speedUnit = "knot",
                durationStr = "00:00:06",
                durationMillis = 6000L,
                dateStr = "Oct 14, 2026",
                fromTime = "01:54",
                toTime = "01:54",
                fromAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
                toAddress = "H9VM+WQC, Desai Village, Noida, Uttar Pradesh 201309, India",
                latitude = 28.5967,
                longitude = 77.3840
            ),
            TripRecord(
                id = "mock_1",
                title = "Trip-1",
                vehicleMode = 0, // Walk
                distance = "0",
                distanceUnit = "nm",
                avgSpeed = "0",
                maxSpeed = "0",
                speedUnit = "knot",
                durationStr = "00:00:11",
                durationMillis = 11000L,
                dateStr = "Oct 12, 2026",
                fromTime = "16:58",
                toTime = "16:58",
                fromAddress = "Block H, H6, H Block, Sector 63, Noida, Uttar Pradesh 201309, India",
                toAddress = "Block H, H6, H Block, Sector 63, Noida, Uttar Pradesh 201309, India",
                latitude = 28.5967,
                longitude = 77.3840
            )
        )
    }
}
