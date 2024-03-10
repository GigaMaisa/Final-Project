package com.example.final_project.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.R
import com.example.final_project.databinding.RestaurantHomePageItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.home.Restaurant

class RestaurantsRecyclerAdapter : ListAdapter<Restaurant, RestaurantsRecyclerAdapter.RestaurantItemViewHolder>(DIFF_CALLBACK) {
    var onClick: ((Int) -> Unit)? = null

    inner class RestaurantItemViewHolder(private val binding: RestaurantHomePageItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val restaurant: Restaurant = currentList[adapterPosition]

            ivCover.loadImage(restaurant.image)
            tvRestaurantName.text = restaurant.title
            tvRestaurantType.text = restaurant.type

            val deliveryFee = String.format(root.context.getString(R.string.delivery_fee), restaurant.deliveryFee)

            tvDeliveryFee.text = deliveryFee
            tvDeliveryTime.text = restaurant.deliveryTime
            tvRating.text = restaurant.rating.toString()
        }

        fun itemClick() {
            binding.root.setOnClickListener {
                onClick?.invoke(currentList[adapterPosition].restaurantId)
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<Restaurant>() {

            override fun areItemsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem.restaurantId == newItem.restaurantId
            }

            override fun areContentsTheSame(oldItem: Restaurant, newItem: Restaurant): Boolean {
                return oldItem == newItem
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantItemViewHolder {
        return RestaurantItemViewHolder(RestaurantHomePageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantItemViewHolder, position: Int) {
        holder.bind()
        holder.itemClick()
    }
}