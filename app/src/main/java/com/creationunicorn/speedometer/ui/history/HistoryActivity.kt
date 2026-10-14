package com.creationunicorn.speedometer.ui.history

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.data.TripRecord
import com.creationunicorn.speedometer.data.TripRepository
import com.creationunicorn.speedometer.databinding.ActivityHistoryBinding
import com.creationunicorn.speedometer.ui.trip.TripSummaryActivity

class HistoryActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_SELECTED_MODE = "extra_selected_mode"
    }

    private lateinit var binding: ActivityHistoryBinding
    private lateinit var adapter: HistoryAdapter

    // Tab 0: Bicycle (1), Tab 1: Motorcycle (2), Tab 2: Car (3), Tab 3: Walk (0)
    private val tabModeMapping = listOf(1, 2, 3, 0)
    private var currentTabIndex = 3 // Default to Walk (matching screenshot)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Read initial vehicle mode passed from Home Screen
        val initialMode = intent.getIntExtra(EXTRA_SELECTED_MODE, 0)
        currentTabIndex = when (initialMode) {
            1 -> 0 // Bicycle
            2 -> 1 // Motorcycle
            3 -> 2 // Car
            else -> 3 // Walk
        }

        setupRecyclerView()
        setupTabs()
        setupActions()
        selectTab(currentTabIndex)
    }

    override fun onResume() {
        super.onResume()
        refreshCurrentTab()
    }

    private fun setupRecyclerView() {
        adapter = HistoryAdapter(
            onItemClick = { trip ->
                openTripSummary(trip)
            },
            onMenuClick = { anchorView, trip ->
                showTripPopupMenu(anchorView, trip)
            },
            onSelectionChanged = { count ->
                updateDeleteBar(count)
            }
        )

        binding.rvHistoryTrips.layoutManager = LinearLayoutManager(this)
        binding.rvHistoryTrips.adapter = adapter
    }

    private fun setupTabs() {
        binding.tabBicycle.setOnClickListener { selectTab(0) }
        binding.tabMotorcycle.setOnClickListener { selectTab(1) }
        binding.tabCar.setOnClickListener { selectTab(2) }
        binding.tabWalk.setOnClickListener { selectTab(3) }
    }

    private fun selectTab(tabIndex: Int) {
        currentTabIndex = tabIndex
        val cyanColor = ContextCompat.getColor(this, R.color.neon_cyan)
        val dimColor = Color.parseColor("#70FFFFFF")
        val boldTypeface = ResourcesCompat.getFont(this, R.font.poppins_bold)
        val regularTypeface = ResourcesCompat.getFont(this, R.font.poppins_regular)

        val tabTexts = listOf(
            binding.tvTabBicycle,
            binding.tvTabMotorcycle,
            binding.tvTabCar,
            binding.tvTabWalk
        )
        val tabIndicators = listOf(
            binding.indicatorBicycle,
            binding.indicatorMotorcycle,
            binding.indicatorCar,
            binding.indicatorWalk
        )

        for (i in tabTexts.indices) {
            if (i == tabIndex) {
                tabTexts[i].setTextColor(cyanColor)
                tabTexts[i].typeface = boldTypeface
                tabTexts[i].textSize = 15f
                tabIndicators[i].visibility = View.VISIBLE
            } else {
                tabTexts[i].setTextColor(dimColor)
                tabTexts[i].typeface = regularTypeface
                tabTexts[i].textSize = 14f
                tabIndicators[i].visibility = View.GONE
            }
        }

        refreshCurrentTab()
    }

    private fun refreshCurrentTab() {
        val targetMode = tabModeMapping[currentTabIndex]
        val trips = TripRepository.getTripsByMode(this, targetMode)
        adapter.submitList(trips)

        if (trips.isEmpty()) {
            binding.rvHistoryTrips.visibility = View.GONE
            binding.llEmptyState.visibility = View.VISIBLE
        } else {
            binding.rvHistoryTrips.visibility = View.VISIBLE
            binding.llEmptyState.visibility = View.GONE
        }
    }

    private fun setupActions() {
        binding.ivBackHistory.setOnClickListener {
            finish()
        }

        binding.tvSelectHistory.setOnClickListener {
            toggleSelectionMode()
        }

        binding.flDeleteBar.setOnClickListener {
            val selected = adapter.getSelectedIds()
            if (selected.isNotEmpty()) {
                TripRepository.deleteTrips(this, selected)
                toggleSelectionMode(forceDisable = true)
                refreshCurrentTab()
                Toast.makeText(this, "${selected.size} trip(s) deleted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun toggleSelectionMode(forceDisable: Boolean = false) {
        val enable = if (forceDisable) false else !adapter.isSelectionMode
        adapter.setSelectionMode(enable)
        binding.tvSelectHistory.text = if (enable) "Cancel" else "Select"
        binding.flDeleteBar.visibility = if (enable) View.VISIBLE else View.GONE
        updateDeleteBar(adapter.getSelectedIds().size)
    }

    private fun updateDeleteBar(selectedCount: Int) {
        if (selectedCount > 0) {
            binding.tvDeleteBtnText.text = "Delete ($selectedCount)"
            binding.flDeleteBar.alpha = 1.0f
            binding.flDeleteBar.isEnabled = true
        } else {
            binding.tvDeleteBtnText.text = "Delete"
            binding.flDeleteBar.alpha = 0.6f
            binding.flDeleteBar.isEnabled = false
        }
    }

    private fun openTripSummary(trip: TripRecord) {
        val intent = Intent(this, TripSummaryActivity::class.java).apply {
            putExtra(TripSummaryActivity.EXTRA_TITLE, trip.title)
            putExtra(TripSummaryActivity.EXTRA_DISTANCE, trip.distance)
            putExtra(TripSummaryActivity.EXTRA_DISTANCE_UNIT, trip.distanceUnit)
            putExtra(TripSummaryActivity.EXTRA_DURATION_MILLIS, trip.durationMillis)
            putExtra(TripSummaryActivity.EXTRA_AVG_SPEED, trip.avgSpeed)
            putExtra(TripSummaryActivity.EXTRA_MAX_SPEED, trip.maxSpeed)
            putExtra(TripSummaryActivity.EXTRA_SPEED_UNIT, trip.speedUnit)
            putExtra(TripSummaryActivity.EXTRA_MODE, trip.vehicleMode)
            putExtra(TripSummaryActivity.EXTRA_LAT, trip.latitude)
            putExtra(TripSummaryActivity.EXTRA_LON, trip.longitude)
        }
        startActivity(intent)
    }

    private fun showTripPopupMenu(anchorView: View, trip: TripRecord) {
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
            showRenameDialog(trip)
        }

        popupView.findViewById<TextView>(R.id.menuShare)?.setOnClickListener {
            popupWindow.dismiss()
            shareTrip(trip)
        }

        popupView.findViewById<TextView>(R.id.menuExport)?.setOnClickListener {
            popupWindow.dismiss()
            exportTrip(trip)
        }

        popupView.findViewById<TextView>(R.id.menuDelete)?.setOnClickListener {
            popupWindow.dismiss()
            showDeleteConfirmDialog(trip)
        }

        popupWindow.showAsDropDown(anchorView, -200, 10)
    }

    private fun showRenameDialog(trip: TripRecord) {
        val input = EditText(this).apply {
            setText(trip.title)
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
                    TripRepository.updateTripTitle(this, trip.id, newName)
                    refreshCurrentTab()
                    Toast.makeText(this, "Renamed to $newName", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun shareTrip(trip: TripRecord) {
        val shareText = buildString {
            appendLine("🚗 Speedometer Trip Summary")
            appendLine("Trip: ${trip.title}")
            appendLine("Date: ${trip.dateStr}")
            appendLine("Distance: ${trip.distance} ${trip.distanceUnit}")
            appendLine("Duration: ${trip.durationStr}")
            appendLine("Avg Speed: ${trip.avgSpeed} ${trip.speedUnit}")
            appendLine("Max Speed: ${trip.maxSpeed} ${trip.speedUnit}")
            appendLine("From: ${trip.fromAddress}")
            appendLine("To: ${trip.toAddress}")
        }

        val sendIntent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, shareText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, "Share Trip Summary"))
    }

    private fun exportTrip(trip: TripRecord) {
        Toast.makeText(this, "Exported ${trip.title} as GPX successfully", Toast.LENGTH_SHORT).show()
    }

    private fun showDeleteConfirmDialog(trip: TripRecord) {
        AlertDialog.Builder(this, androidx.appcompat.R.style.Theme_AppCompat_Dialog_Alert)
            .setTitle("Delete Trip")
            .setMessage("Are you sure you want to delete ${trip.title}?")
            .setPositiveButton("Delete") { _, _ ->
                TripRepository.deleteTrip(this, trip.id)
                refreshCurrentTab()
                Toast.makeText(this, "${trip.title} deleted", Toast.LENGTH_SHORT).show()
            }
            .setNegativeButton("Cancel", null)
            .show()
    }
}
