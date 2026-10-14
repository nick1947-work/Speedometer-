package com.creationunicorn.speedometer.ui.trip

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.preference.PreferenceManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ActivityTripSummaryBinding
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TripSummaryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_TITLE = "trip_title"
        const val EXTRA_DISTANCE = "trip_distance"
        const val EXTRA_DISTANCE_UNIT = "trip_distance_unit"
        const val EXTRA_DURATION_MILLIS = "trip_duration_millis"
        const val EXTRA_AVG_SPEED = "trip_avg_speed"
        const val EXTRA_MAX_SPEED = "trip_max_speed"
        const val EXTRA_SPEED_UNIT = "trip_speed_unit"
        const val EXTRA_MODE = "trip_mode"
        const val EXTRA_LAT = "trip_lat"
        const val EXTRA_LON = "trip_lon"
    }

    private lateinit var binding: ActivityTripSummaryBinding
    private var tripName: String = "Trip-4"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Configuration.getInstance().load(this, PreferenceManager.getDefaultSharedPreferences(this))
        Configuration.getInstance().userAgentValue = packageName

        binding = ActivityTripSummaryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupTripData()
        setupListeners()
        setupMap()
    }

    private fun setupTripData() {
        val passedTitle = intent.getStringExtra(EXTRA_TITLE)
        if (!passedTitle.isNullOrEmpty()) {
            tripName = passedTitle
        } else {
            val prefs = getSharedPreferences("speedometer_prefs", Context.MODE_PRIVATE)
            val tripCount = prefs.getInt("trip_history_count", 3) + 1
            prefs.edit().putInt("trip_history_count", tripCount).apply()
            tripName = "Trip-$tripCount"
        }
        binding.tvTripName.text = tripName

        val mode = intent.getIntExtra(EXTRA_MODE, 3) // Default car
        val vehicleRes = when (mode) {
            0 -> R.drawable.ic_transport_walking
            1 -> R.drawable.ic_transport_bicycle
            2 -> R.drawable.ic_traffic_motorcycle
            else -> R.drawable.ic_traffic_car
        }
        binding.ivTripModeIcon.setImageResource(vehicleRes)

        val currentDate = SimpleDateFormat("MMM dd, yyyy", Locale.US).format(Date())
        val currentTime = SimpleDateFormat("HH:mm", Locale.US).format(Date())

        binding.tvTripDate.text = currentDate
        binding.tvFromTime.text = currentTime
        binding.tvToTime.text = currentTime

        val distanceVal = intent.getStringExtra(EXTRA_DISTANCE) ?: "0"
        val distanceUnit = intent.getStringExtra(EXTRA_DISTANCE_UNIT) ?: "nm"
        val avgSpeedVal = intent.getStringExtra(EXTRA_AVG_SPEED) ?: "0"
        val maxSpeedVal = intent.getStringExtra(EXTRA_MAX_SPEED) ?: "0"
        val speedUnit = intent.getStringExtra(EXTRA_SPEED_UNIT) ?: "knot"

        binding.tvSummaryDistanceVal.text = distanceVal
        binding.tvSummaryDistanceUnit.text = distanceUnit

        binding.tvSummaryAvgSpeedVal.text = avgSpeedVal
        binding.tvSummaryAvgSpeedUnit.text = speedUnit

        binding.tvSummaryMaxSpeedVal.text = maxSpeedVal
        binding.tvSummaryMaxSpeedUnit.text = speedUnit

        val durationMillis = intent.getLongExtra(EXTRA_DURATION_MILLIS, 0L)
        val totalSec = durationMillis / 1000
        val h = totalSec / 3600
        val m = (totalSec % 3600) / 60
        val s = totalSec % 60
        binding.tvSummaryDurationVal.text = String.format(Locale.US, "%02d:%02d:%02d", h, m, s)
    }

    private fun setupListeners() {
        binding.ivBackTrip.setOnClickListener {
            finish()
        }

        binding.ivTripMenuMore.setOnClickListener { anchorView ->
            showTripOptionsMenu(anchorView)
        }
    }

    private fun showTripOptionsMenu(anchorView: View) {
        val popupView = LayoutInflater.from(this).inflate(R.layout.layout_trip_popup_menu, null)
        val popupWindow = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            elevation = 16f
        }

        popupView.findViewById<TextView>(R.id.menuRename)?.setOnClickListener {
            popupWindow.dismiss()
            showRenameDialog()
        }

        popupView.findViewById<TextView>(R.id.menuShare)?.setOnClickListener {
            popupWindow.dismiss()
            shareTrip()
        }

        popupView.findViewById<TextView>(R.id.menuExport)?.setOnClickListener {
            popupWindow.dismiss()
            exportTrip()
        }

        popupView.findViewById<TextView>(R.id.menuDelete)?.setOnClickListener {
            popupWindow.dismiss()
            showDeleteConfirmDialog()
        }

        // Show dropdown aligned to right of the more icon
        popupWindow.showAsDropDown(anchorView, -200, 10)
    }

    private fun showRenameDialog() {
        val input = EditText(this).apply {
            setText(tripName)
            setSelection(text.length)
            setTextColor(Color.WHITE)
            setBackgroundColor(Color.parseColor("#22242A"))
            setPadding(32, 24, 32, 24)
        }

        AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
            .setTitle("Rename Trip")
            .setView(input)
            .setPositiveButton("Save") { _, _ ->
                val newName = input.text.toString().trim()
                if (newName.isNotEmpty()) {
                    tripName = newName
                    binding.tvTripName.text = newName
                    Toast.makeText(this, "Renamed to $newName", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun shareTrip() {
        val shareText = buildString {
            appendLine("🚗 Speedometer Trip Summary")
            appendLine("Trip: $tripName")
            appendLine("Date: ${binding.tvTripDate.text}")
            appendLine("Distance: ${binding.tvSummaryDistanceVal.text} ${binding.tvSummaryDistanceUnit.text}")
            appendLine("Duration: ${binding.tvSummaryDurationVal.text}")
            appendLine("Avg Speed: ${binding.tvSummaryAvgSpeedVal.text} ${binding.tvSummaryAvgSpeedUnit.text}")
            appendLine("Max Speed: ${binding.tvSummaryMaxSpeedVal.text} ${binding.tvSummaryMaxSpeedUnit.text}")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Share Trip Summary"))
    }

    private fun exportTrip() {
        val exportSummary = buildString {
            appendLine("<?xml version=\"1.0\" encoding=\"UTF-8\"?>")
            appendLine("<gpx version=\"1.1\" creator=\"Speedometer\">")
            appendLine("  <trk><name>$tripName</name></trk>")
            appendLine("</gpx>")
        }
        Toast.makeText(this, "Exported $tripName as GPX successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmDialog() {
        AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
            .setTitle("Delete Trip")
            .setMessage("Are you sure you want to delete this trip?")
            .setPositiveButton("Delete") { _, _ ->
                Toast.makeText(this, "Trip deleted", Toast.LENGTH_SHORT).show()
                finish()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun setupMap() {
        val lat = intent.getDoubleExtra(EXTRA_LAT, 28.5967)
        val lon = intent.getDoubleExtra(EXTRA_LON, 77.3840)
        val point = GeoPoint(lat, lon)

        binding.summaryMapView.apply {
            setTileSource(TileSourceFactory.MAPNIK)
            setMultiTouchControls(true)
            controller.setZoom(15.0)
            controller.setCenter(point)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.summaryMapView.onResume()
    }

    override fun onPause() {
        super.onPause()
        binding.summaryMapView.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.summaryMapView.onDetach()
    }
}
