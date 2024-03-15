package com.example.final_project.presentation.screen.restoraunt_details.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerOptionsItemBinding
import com.example.final_project.presentation.model.restaurant.MenuItemAdditions

class RestaurantOptionRecyclerViewAdapter: ListAdapter<MenuItemAdditions, RestaurantOptionRecyclerViewAdapter.RestaurantOptionsViewHolder>(
    RestaurantMenuItemDiffCallback
) {

    var onOptionClick: ((MenuItemAdditions) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RestaurantOptionsViewHolder {
        return RestaurantOptionsViewHolder(
            RecyclerOptionsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RestaurantOptionsViewHolder, position: Int) {
        holder.bind()
    }

    inner class RestaurantOptionsViewHolder(private val binding: RecyclerOptionsItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val option = currentList[adapterPosition]
            binding.tvOptionTitle.text = option.category
            with(binding.recyclerOptions) {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = RestaurantOptionSelectionRecyclerViewAdapter().apply {
                    submitList(option.options)
                    onClick = {menuItem ->
                        val updatedOptions = option.options.map {
                            it.copy(selected = it.name == menuItem.name)
                        }
                        submitList(updatedOptions)
                        onOptionClick?.invoke(option.copy(options = updatedOptions))
                    }
                }
            }
        }
    }

    companion object {
        private val RestaurantMenuItemDiffCallback = object : DiffUtil.ItemCallback<MenuItemAdditions>() {

            override fun areItemsTheSame(oldItem: MenuItemAdditions, newItem: MenuItemAdditions): Boolean {
                return oldItem.category == newItem.category
            }

            override fun areContentsTheSame(oldItem: MenuItemAdditions, newItem: MenuItemAdditions): Boolean {
                return oldItem == newItem
            }
        }
    }
}