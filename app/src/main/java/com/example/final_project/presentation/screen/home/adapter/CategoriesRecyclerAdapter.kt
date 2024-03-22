package com.example.final_project.presentation.screen.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerCategoryItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.extension.toRestaurantType
import com.example.final_project.presentation.model.home.CategoryType
import com.example.final_project.presentation.model.restaurant.RestaurantType

class CategoriesRecyclerAdapter  : ListAdapter<CategoryType, CategoriesRecyclerAdapter.CategoryViewHolder>(DIFF_CALLBACK) {

    var onClick: ((RestaurantType) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        return CategoryViewHolder(RecyclerCategoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind()
    }

    inner class CategoryViewHolder(private val binding: RecyclerCategoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val category = currentList[adapterPosition]
            shapeableImageViewCover.loadImage(category.imageUrl)
            tvType.text = category.type
            tvNumberOfRestaurants.text = category.numberOfRestaurants.toString()
            itemClick()
        }

        private fun itemClick() {
            binding.root.setOnClickListener {
                onClick?.invoke(currentList[adapterPosition].type.toRestaurantType())
            }
        }
    }

    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<CategoryType>() {

            override fun areItemsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem.type == newItem.type
            }

            override fun areContentsTheSame(oldItem: CategoryType, newItem: CategoryType): Boolean {
                return oldItem == newItem
            }
        }
    }
}