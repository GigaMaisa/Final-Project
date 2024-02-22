package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.model.cart.Checkout

data class CartState(
    val cartItems: List<CartItem>? = null,
    val checkout: Checkout? = null
)