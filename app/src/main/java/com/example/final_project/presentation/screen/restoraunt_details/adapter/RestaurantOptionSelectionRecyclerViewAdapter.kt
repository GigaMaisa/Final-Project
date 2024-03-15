package com.example.final_project.presentation.screen.restoraunt_details.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerOptionRadioItemBinding
import com.example.final_project.presentation.model.restaurant.MenuItemAdditionsOptions
import com.example.final_project.presentation.model.restaurant.MenuItemDetails

class RestaurantOptionSelectionRecyclerViewAdapter: ListAdapter<MenuItemAdditionsOptions, RestaurantOptionSelectionRecyclerViewAdapter.RestaurantOptionsRadioViewHolder>(
    RestaurantMenuItemDiffCallback
) {

    var onClick: ((MenuItemAdditionsOptions) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOptionsRadioViewHolder {
        return RestaurantOptionsRadioViewHolder(
            RecyclerOptionRadioItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantOptionsRadioViewHolder, position: Int) {
        holder.bind()
    }

    inner class RestaurantOptionsRadioViewHolder(private val binding: RecyclerOptionRadioItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val option = currentList[adapterPosition]
            binding.radioButton.isChecked = option.selected
            binding.tvRadioText.text = option.name
            binding.radioButton.setOnClickListener {
                onClick?.invoke(option.copy(selected = !option.selected))
            }
        }
    }

    companion object {
        private val RestaurantMenuItemDiffCallback = object : DiffUtil.ItemCallback<MenuItemAdditionsOptions>() {

            override fun areItemsTheSame(oldItem: MenuItemAdditionsOptions, newItem: MenuItemAdditionsOptions): Boolean {
                return oldItem.name == newItem.name
            }

            override fun areContentsTheSame(oldItem: MenuItemAdditionsOptions, newItem: MenuItemAdditionsOptions): Boolean {
                return oldItem == newItem
            }
        }
    }
}