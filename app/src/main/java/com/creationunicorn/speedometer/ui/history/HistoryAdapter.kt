package com.creationunicorn.speedometer.ui.history

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.data.TripRecord
import com.creationunicorn.speedometer.databinding.ItemHistoryTripBinding

class HistoryAdapter(
    private val onItemClick: (TripRecord) -> Unit,
    private val onMenuClick: (View, TripRecord) -> Unit,
    private val onSelectionChanged: (Int) -> Unit
) : RecyclerView.Adapter<HistoryAdapter.TripViewHolder>() {

    private val trips = mutableListOf<TripRecord>()
    var isSelectionMode = false
        private set
    private val selectedIds = mutableSetOf<String>()

    fun submitList(newList: List<TripRecord>) {
        trips.clear()
        trips.addAll(newList)
        notifyDataSetChanged()
    }

    fun setSelectionMode(enabled: Boolean) {
        if (isSelectionMode != enabled) {
            isSelectionMode = enabled
            if (!enabled) {
                selectedIds.clear()
            }
            notifyDataSetChanged()
            onSelectionChanged(selectedIds.size)
        }
    }

    fun getSelectedIds(): Set<String> = selectedIds

    fun clearSelection() {
        selectedIds.clear()
        notifyDataSetChanged()
        onSelectionChanged(0)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
        val binding = ItemHistoryTripBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return TripViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
        val item = trips[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int = trips.size

    inner class TripViewHolder(private val binding: ItemHistoryTripBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(trip: TripRecord) {
            // Title
            binding.tvItemTripTitle.text = trip.title

            // Mode icon
            val iconRes = when (trip.vehicleMode) {
                0 -> R.drawable.ic_transport_walking
                1 -> R.drawable.ic_transport_bicycle
                2 -> R.drawable.ic_traffic_motorcycle
                else -> R.drawable.ic_traffic_car
            }
            binding.ivItemTripIcon.setImageResource(iconRes)

            // Metrics
            binding.tvItemDistance.text = "${trip.distance} ${trip.distanceUnit}"
            binding.tvItemDate.text = trip.dateStr
            binding.tvItemSpeed.text = "${trip.avgSpeed} ${trip.speedUnit}"
            binding.tvItemDuration.text = trip.durationStr

            // Addresses & times
            binding.tvItemFromTime.text = trip.fromTime
            binding.tvItemEndTime.text = trip.toTime
            binding.tvItemFromAddress.text = trip.fromAddress
            binding.tvItemToAddress.text = trip.toAddress

            // Selection mode UI
            if (isSelectionMode) {
                binding.ivItemTripMenu.visibility = View.GONE
                binding.ivItemTripCheck.visibility = View.VISIBLE
                val isSelected = selectedIds.contains(trip.id)
                binding.ivItemTripCheck.setImageResource(
                    if (isSelected) R.drawable.ic_icon_history_selected
                    else R.drawable.ic_icon_history_unselected
                )
            } else {
                binding.ivItemTripMenu.visibility = View.VISIBLE
                binding.ivItemTripCheck.visibility = View.GONE
            }

            // Click listener
            itemView.setOnClickListener {
                if (isSelectionMode) {
                    if (selectedIds.contains(trip.id)) {
                        selectedIds.remove(trip.id)
                    } else {
                        selectedIds.add(trip.id)
                    }
                    notifyItemChanged(adapterPosition)
                    onSelectionChanged(selectedIds.size)
                } else {
                    onItemClick(trip)
                }
            }

            // 3-dots more menu click
            binding.ivItemTripMenu.setOnClickListener { v ->
                onMenuClick(v, trip)
            }
        }
    }
}
