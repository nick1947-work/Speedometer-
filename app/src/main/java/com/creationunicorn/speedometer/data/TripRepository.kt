package com.creationunicorn.speedometer.data

import android.content.Context
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TripRepository {

    private const val PREFS_NAME = "speedometer_trips_store"
    private const val KEY_TRIPS = "key_stored_trips"
    private const val KEY_SEEDED_VERSION = "key_is_trips_seeded_v3"

    fun getAllTrips(context: Context): MutableList<TripRecord> {
        val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
        val jsonStr = prefs.getString(KEY_TRIPS, null)
        val list = mutableListOf<TripRecord>()
        if (!jsonStr.isNullOrEmpty()) {
            try {
                val arr = JSONArray(jsonStr)
                for (i in 0 until arr.length()) {
                    list.add(TripRecord.fromJson(arr.getJSONObject(i)))
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        val seededV3 = prefs.getBoolean(KEY_SEEDED_VERSION, false)
        if (!seededV3 || list.isEmpty() || !hasAllVehicleModes(list)) {
            val initial = getSeedTrips()
            val existingIds = list.map { it.id }.toSet()
            val merged = ArrayList<TripRecord>(list)
            for (seed in initial) {
                if (seed.id !in existingIds) {
                    merged.add(seed)
                }
            }
            saveAllTrips(context, merged)
            prefs.edit().putBoolean(KEY_SEEDED_VERSION, true).apply()
            return merged
        }

        return list
    }

    private fun hasAllVehicleModes(list: List<TripRecord>): Boolean {
        val modes = list.map { it.vehicleMode }.toSet()
        return modes.contains(0) && modes.contains(1) && modes.contains(2) && modes.contains(3)
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
            // Car Trips (mode 3)
            TripRecord(
                id = "car_4",
                title = "Trip-4",
                vehicleMode = 3, // Car
                distance = "12",
                distanceUnit = "km",
                avgSpeed = "45",
                maxSpeed = "68",
                speedUnit = "km/h",
                durationStr = "00:18:24",
                durationMillis = 1104000L,
                dateStr = "Oct 14, 2026",
                fromTime = "02:31",
                toTime = "02:49",
                fromAddress = "Sector 62, Noida, Uttar Pradesh, India",
                toAddress = "Connaught Place, Central Delhi, Delhi, India",
                latitude = 28.6289,
                longitude = 77.2065
            ),
            TripRecord(
                id = "car_3",
                title = "Trip-3",
                vehicleMode = 3, // Car
                distance = "5.4",
                distanceUnit = "km",
                avgSpeed = "38",
                maxSpeed = "55",
                speedUnit = "km/h",
                durationStr = "00:11:15",
                durationMillis = 675000L,
                dateStr = "Oct 13, 2026",
                fromTime = "18:20",
                toTime = "18:31",
                fromAddress = "Indirapuram, Ghaziabad, Uttar Pradesh, India",
                toAddress = "Electronic City, Sector 63, Noida, India",
                latitude = 28.6360,
                longitude = 77.3620
            ),

            // Motorcycle Trips (mode 2)
            TripRecord(
                id = "motor_2",
                title = "Trip-2",
                vehicleMode = 2, // Motorcycle
                distance = "8.6",
                distanceUnit = "km",
                avgSpeed = "32",
                maxSpeed = "48",
                speedUnit = "km/h",
                durationStr = "00:16:10",
                durationMillis = 970000L,
                dateStr = "Oct 14, 2026",
                fromTime = "10:15",
                toTime = "10:31",
                fromAddress = "Botanical Garden, Sector 38, Noida, India",
                toAddress = "Sector 62, Noida, Uttar Pradesh, India",
                latitude = 28.5645,
                longitude = 77.3344
            ),
            TripRecord(
                id = "motor_1",
                title = "Trip-1",
                vehicleMode = 2, // Motorcycle
                distance = "4.2",
                distanceUnit = "km",
                avgSpeed = "28",
                maxSpeed = "42",
                speedUnit = "km/h",
                durationStr = "00:09:40",
                durationMillis = 580000L,
                dateStr = "Oct 12, 2026",
                fromTime = "14:10",
                toTime = "14:20",
                fromAddress = "Atta Market, Sector 18, Noida, India",
                toAddress = "Sector 63, Noida, India",
                latitude = 28.5708,
                longitude = 77.3271
            ),

            // Bicycle Trips (mode 1)
            TripRecord(
                id = "bike_2",
                title = "Trip-2",
                vehicleMode = 1, // Bicycle
                distance = "3.5",
                distanceUnit = "km",
                avgSpeed = "16",
                maxSpeed = "22",
                speedUnit = "km/h",
                durationStr = "00:14:20",
                durationMillis = 860000L,
                dateStr = "Oct 14, 2026",
                fromTime = "07:30",
                toTime = "07:44",
                fromAddress = "Central Park, Sector 62, Noida, India",
                toAddress = "Green Valley, Sector 63, Noida, India",
                latitude = 28.6250,
                longitude = 77.3680
            ),
            TripRecord(
                id = "bike_1",
                title = "Trip-1",
                vehicleMode = 1, // Bicycle
                distance = "2.1",
                distanceUnit = "km",
                avgSpeed = "14",
                maxSpeed = "19",
                speedUnit = "km/h",
                durationStr = "00:09:05",
                durationMillis = 545000L,
                dateStr = "Oct 13, 2026",
                fromTime = "06:45",
                toTime = "06:54",
                fromAddress = "Block D, Sector 62, Noida, India",
                toAddress = "Block H, Sector 63, Noida, India",
                latitude = 28.6270,
                longitude = 77.3710
            ),

            // Walk Trips (mode 0)
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
                durationStr = "00:00:00",
                durationMillis = 0L,
                dateStr = "Oct 14, 2026",
                fromTime = "01:54",
                toTime = "01:54",
                fromAddress = "--",
                toAddress = "--",
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
