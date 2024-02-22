package com.example.final_project.presentation.screen.cart.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.final_project.databinding.RecyclerCartItemBinding
import com.example.final_project.databinding.RecyclerCheckoutCartItemBinding
import com.example.final_project.presentation.extension.loadImage
import com.example.final_project.presentation.model.cart.CartCheckout

class CartRecyclerViewAdapter :
    ListAdapter<CartCheckout, ViewHolder>(CartItemDiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            CART_ITEM ->  CartItemViewHolder(RecyclerCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
            CHECKOUT_ITEM -> CheckoutCartItemViewHolder((RecyclerCheckoutCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)))
            else -> CartItemViewHolder(RecyclerCartItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        when(holder) {
            is CartItemViewHolder -> holder.bind()
            is CheckoutCartItemViewHolder -> holder.bind()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(position) {
            currentList.size - 1 -> CHECKOUT_ITEM
            else -> CART_ITEM
        }
    }

    inner class CartItemViewHolder(private val binding: RecyclerCartItemBinding) : ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val cartItem = currentList[adapterPosition].cartItem!!
            with(cartItem) {
                imageViewCover.loadImage(cover)
                tvTitle.text = title
                tvCategory.text = category
                tvQuantity.text = quantity.toString()
                tvPrice.text = price.toString().plus(" ₾")
            }
        }
    }

    inner class CheckoutCartItemViewHolder(private val binding: RecyclerCheckoutCartItemBinding) : ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val checkout = currentList[adapterPosition].checkout!!
            tvSubTotalPrice.text = checkout.subTotal.toString().plus(" ₾")
            tvDeliveryChargePrice.text = checkout.chargeTotal.toString().plus(" ₾")
            tvTotalPrice.text = (checkout.subTotal.plus(checkout.chargeTotal)).toString().plus(" ₾")
        }
    }

    companion object {
        const val CART_ITEM = 1
        const val CHECKOUT_ITEM = 2
        private val CartItemDiffCallback = object : DiffUtil.ItemCallback<CartCheckout>() {

            override fun areItemsTheSame(oldItem: CartCheckout, newItem: CartCheckout): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: CartCheckout, newItem: CartCheckout): Boolean {
                return oldItem == newItem
            }
        }
    }
}