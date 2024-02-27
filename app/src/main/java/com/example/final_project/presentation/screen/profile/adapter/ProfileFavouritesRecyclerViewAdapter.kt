package com.example.final_project.presentation.screen.profile.adapter

import android.util.Log.d
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.final_project.databinding.RecyclerCartItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.cart.CartItem

class ProfileFavouritesRecyclerViewAdapter : ListAdapter<CartItem, ProfileFavouritesRecyclerViewAdapter.FavouritesItemViewHolder>(
    FavouritesMenuItemDiffCallback
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesItemViewHolder {
        return FavouritesItemViewHolder(
            RecyclerCartItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: FavouritesItemViewHolder, position: Int) {
        holder.bind()
    }

    inner class FavouritesItemViewHolder(private val binding: RecyclerCartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind() = with(binding) {
            d("showList", currentList.toString())
            val cartItem = currentList[adapterPosition]
            with(cartItem) {
                imageViewCover.loadImage(cover)
                tvTitle.text = title
                tvCategory.text = category
                tvQuantity.text = quantity.toString()
                tvPrice.text = price.toString().plus(" ₾")
            }
        }
    }

    companion object {
        private val FavouritesMenuItemDiffCallback = object : DiffUtil.ItemCallback<CartItem>() {

            override fun areItemsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CartItem, newItem: CartItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}