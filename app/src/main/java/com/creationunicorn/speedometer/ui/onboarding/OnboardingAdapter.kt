package com.creationunicorn.speedometer.ui.onboarding

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.creationunicorn.speedometer.R
import com.creationunicorn.speedometer.databinding.ItemOnboardingPageBinding

data class OnboardingSlide(
    val titleRes: Int,
    val descRes: Int,
    val illustrationRes: Int
)

class OnboardingAdapter(
    private val slides: List<OnboardingSlide>
) : RecyclerView.Adapter<OnboardingAdapter.SlideViewHolder>() {

    inner class SlideViewHolder(val binding: ItemOnboardingPageBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlideViewHolder {
        val binding = ItemOnboardingPageBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return SlideViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlideViewHolder, position: Int) {
        val slide = slides[position]
        holder.binding.tvOnboardingTitle.setText(slide.titleRes)
        holder.binding.tvOnboardingDesc.setText(slide.descRes)
        holder.binding.ivOnboardingIllustration.setImageResource(slide.illustrationRes)
    }

    override fun getItemCount(): Int = slides.size
}
