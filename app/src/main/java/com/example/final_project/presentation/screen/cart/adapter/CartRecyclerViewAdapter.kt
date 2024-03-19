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
import com.example.final_project.presentation.model.order.Order

class CartRecyclerViewAdapter : ListAdapter<CartCheckout, ViewHolder>(CartItemDiffCallback) {

    var onPLusClick: ((Order) -> Unit)? = null
    var onMinusClick: ((Order) -> Unit)? = null
    var onPlaceMyOrderClick: ((Double) -> Unit)? = null

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
                imageViewCover.loadImage(image)
                tvTitle.text = name
                tvCategory.text = menuCategory
                tvQuantity.text = quantity.toString()
                tvPrice.text = String.format("%.2f ₾", price)

                btnAddItem.setOnClickListener {
                    onPLusClick?.invoke(cartItem)
                }

                btnRemoveItem.setOnClickListener {
                    onMinusClick?.invoke(cartItem)
                }
            }
        }
    }

    inner class CheckoutCartItemViewHolder(private val binding: RecyclerCheckoutCartItemBinding) : ViewHolder(binding.root) {
        fun bind() = with(binding) {
            val checkout = currentList[adapterPosition].checkout!!
            tvSubTotalPrice.text = String.format("%.2f ₾", checkout.subTotal)
            tvDeliveryChargePrice.text = String.format("%.2f ₾", checkout.chargeTotal)
            tvTotalPrice.text = String.format("%.2f ₾", checkout.subTotal + checkout.chargeTotal)
            binding.btnPlaceOrder.setOnClickListener {
                onPlaceMyOrderClick?.invoke(checkout.subTotal + checkout.chargeTotal)
            }
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