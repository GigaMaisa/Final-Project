package com.example.final_project.presentation.screen.all_restaurants.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.RecyclerRestaurantItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.restaurant.Restaurant

class AllRestaurantsRecyclerViewAdapter: ListAdapter<Restaurant, AllRestaurantsRecyclerViewAdapter.AllRestaurantItemViewHolder>(AllRestaurantsDiffUtils) {
    var onClick: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AllRestaurantItemViewHolder {
        return AllRestaurantItemViewHolder(RecyclerRestaurantItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: AllRestaurantItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class AllRestaurantItemViewHolder(private val binding: RecyclerRestaurantItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val restaurant: Restaurant = currentList[adapterPosition]

            ivCover.loadImage(restaurant.image)
            tvRestaurantName.text = restaurant.title
            tvRestaurantType.text = restaurant.type

            val deliveryFee = String.format(root.context.getString(R.string.delivery_fee), restaurant.deliveryFee)

            tvDeliveryFee.text = deliveryFee
            tvDeliveryTime.text = restaurant.deliveryTime
            tvDeliveryRating.text = restaurant.rating.toString()

            itemClick()
        }

        private fun itemClick() {
            binding.root.setOnClickListener {
                onClick?.invoke(currentList[adapterPosition].restaurantId)
            }
        }
    }

    companion object {
        private val AllRestaurantsDiffUtils = object : DiffUtil.ItemCallback<Restaurant>() {

            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.restaurantId == newItem.restaurantId
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }
}