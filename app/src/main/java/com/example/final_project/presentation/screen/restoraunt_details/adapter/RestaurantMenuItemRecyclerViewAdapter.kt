package com.example.final_project.presentation.screen.restoraunt_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerMenuItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.restaurant.MenuItemDetails

class RestaurantMenuItemRecyclerViewAdapter: ListAdapter<MenuItemDetails, RestaurantMenuItemRecyclerViewAdapter.RestaurantMenuItemViewHolder>(
    RestaurantMenuItemDiffCallback
) {

    var onItemClick: ((MenuItemDetails) -> Unit)? = null
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantMenuItemViewHolder {
        return RestaurantMenuItemViewHolder(RecyclerMenuItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: RestaurantMenuItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class RestaurantMenuItemViewHolder(private val binding: RecyclerMenuItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val menuItem = currentList[adapterPosition]
            tvMenuItemName.text = menuItem.name
            tvMenuDescription.text = menuItem.description
            tvMenuItemPrice.text = menuItem.price.toString().plus(" ₾")
            shapeableImageViewCover.loadImage(menuItem.image)

            binding.root.setOnClickListener {
                onItemClick?.invoke(menuItem)
            }
        }
    }

    companion object {
        private val RestaurantMenuItemDiffCallback = object : DiffUtil.ItemCallback<MenuItemDetails>() {

            override fun areItemsTheSame(oldItem: MenuItemDetails, newItem: MenuItemDetails): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: MenuItemDetails, newItem: MenuItemDetails): Boolean {
                return oldItem == newItem
            }
        }
    }
}