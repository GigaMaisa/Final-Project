package com.example.final_project.presentation.model

data class CartCheckout(
    val id: Int,
    val cartItem: CartItem? = null,
    val checkout: Checkout? = null
)