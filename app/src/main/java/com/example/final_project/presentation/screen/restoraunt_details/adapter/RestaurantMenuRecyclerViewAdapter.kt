package com.example.final_project.presentation.screen.restoraunt_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerRestaurantMenuItemBinding
import com.example.final_project.presentation.model.restaurant.MenuItemDetails
import com.example.final_project.presentation.model.restaurant.RestaurantMenu

class RestaurantMenuRecyclerViewAdapter : ListAdapter<RestaurantMenu, RestaurantMenuRecyclerViewAdapter.RestaurantMenuViewHolder>(
    RestaurantMenuItemDiffCallback
) {

    var onClick: ((MenuItemDetails) -> Unit)? = null

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
            tvMenuCategory.text = menuItem.menuCategory
            recyclerRestaurantMenuItems.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = RestaurantMenuItemRecyclerViewAdapter().apply {
                    submitList(menuItem.items)
                    onItemClick = {
                        onClick?.invoke(it)
                    }
                }
            }
        }
    }

    companion object {
        private val RestaurantMenuItemDiffCallback = object : DiffUtil.ItemCallback<RestaurantMenu>() {

            override fun areItemsTheSame(oldItem: RestaurantMenu, newItem: RestaurantMenu): Boolean {
                return oldItem.menuCategory == newItem.menuCategory
            }

            override fun areContentsTheSame(oldItem: RestaurantMenu, newItem: RestaurantMenu): Boolean {
                return oldItem == newItem
            }
        }
    }
}