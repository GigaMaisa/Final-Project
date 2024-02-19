package com.example.final_project.presentation.screen.restoraunt_details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerRestaurantMenuItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.RestaurantMenu

class RestaurantMenuRecyclerViewAdapter : ListAdapter<RestaurantMenu, RestaurantMenuRecyclerViewAdapter.RestaurantMenuViewHolder>(RestaurantMenuItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantMenuViewHolder {
        return RestaurantMenuViewHolder(
            RecyclerRestaurantMenuItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantMenuViewHolder, position: Int) {
        holder.bind()
    }

    inner class RestaurantMenuViewHolder(private val binding: RecyclerRestaurantMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val menuItem = currentList[adapterPosition]
            with(menuItem) {
                ivCover.loadImage(cover)
                tvTitle.text = title
                tvCategory.text = category
                tvDeliveryPrice.text = deliveryPrice.plus(" ₾")
                tvDeliveryTime.text = deliveryTime.plus(" minutes")
                tvDeliveryRating.text = rating
            }
        }
    }

    companion object {
        private val RestaurantMenuItemDiffCallback = object : DiffUtil.ItemCallback<RestaurantMenu>() {

            override fun areItemsTheSame(oldItem: RestaurantMenu, newItem: RestaurantMenu): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: RestaurantMenu, newItem: RestaurantMenu): Boolean {
                return oldItem == newItem
            }
        }
    }
}