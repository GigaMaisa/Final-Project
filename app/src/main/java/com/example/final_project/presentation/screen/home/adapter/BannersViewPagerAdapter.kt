package com.example.final_project.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.ViewPagerOfferItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.home.Banner

class BannersViewPagerAdapter :
    ListAdapter<Banner, BannersViewPagerAdapter.BannersViewHolder>(BannersItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BannersViewHolder {
        return BannersViewHolder(
            ViewPagerOfferItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: BannersViewHolder, position: Int) {
        holder.bind()
    }

    inner class BannersViewHolder(private val binding: ViewPagerOfferItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() {
            val banner = currentList[adapterPosition]
            with(binding) {
                ivCover.loadImage(banner.image)
                tvSpecialOffer.text = banner.title
            }
        }
    }

    companion object {
        private val BannersItemDiffCallback = object : DiffUtil.ItemCallback<Banner>() {

            override fun areItemsTheSame(oldItem: Banner, newItem: Banner): Boolean {
                return oldItem.restaurantId == newItem.restaurantId
            }

            override fun areContentsTheSame(oldItem: Banner, newItem: Banner): Boolean {
                return oldItem == newItem
            }
        }
    }
}