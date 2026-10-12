package com.creationunicorn.speedometer.ui.history

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.creationunicorn.speedometer.databinding.ActivityHistoryBinding
import com.creationunicorn.speedometer.databinding.ItemHistoryTripBinding
import com.creationunicorn.speedometer.ui.trip.TripSummaryActivity

data class HistoryTripItem(
    val title: String,
    val distance: String,
    val speed: String,
    val duration: String,
    val date: String,
    val fromTime: String,
    val toTime: String,
    val address: String
)

class HistoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHistoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivBackHistory.setOnClickListener {
            finish()
        }

        val trips = listOf(
            HistoryTripItem(
                title = "Trip-1",
                distance = "0 nm",
                speed = "0 knot",
                duration = "00:00:11",
                date = "Oct 12, 2026",
                fromTime = "16:58",
                toTime = "16:58",
                address = "Block H, H6, H Block, Sector 63, Noida, Uttar Pradesh 201309, India"
            )
        )

        binding.rvHistoryTrips.adapter = object : RecyclerView.Adapter<TripViewHolder>() {
            override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TripViewHolder {
                val b = ItemHistoryTripBinding.inflate(LayoutInflater.from(parent.context), parent, false)
                return TripViewHolder(b)
            }

            override fun onBindViewHolder(holder: TripViewHolder, position: Int) {
                val item = trips[position]
                holder.binding.tvItemTripTitle.text = item.title
                holder.binding.tvItemDistance.text = item.distance
                holder.binding.tvItemSpeed.text = "⏱ " + item.speed
                holder.binding.tvItemDuration.text = "⏱ " + item.duration
                holder.binding.tvItemDate.text = item.date
                holder.binding.tvItemFromTime.text = item.fromTime
                holder.binding.tvItemToTime.text = item.toTime
                holder.binding.tvItemFromAddress.text = item.address
                holder.binding.tvItemToAddress.text = item.address

                holder.itemView.setOnClickListener {
                    val intent = Intent(this@HistoryActivity, TripSummaryActivity::class.java).apply {
                        putExtra(TripSummaryActivity.EXTRA_DISTANCE, item.distance)
                        putExtra(TripSummaryActivity.EXTRA_AVG_SPEED, item.speed)
                        putExtra(TripSummaryActivity.EXTRA_MAX_SPEED, item.speed)
                    }
                    startActivity(intent)
                }
            }

            override fun getItemCount(): Int = trips.size
        }
    }

    class TripViewHolder(val binding: ItemHistoryTripBinding) : RecyclerView.ViewHolder(binding.root)
}
