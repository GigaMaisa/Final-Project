package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.CartItem
import com.example.final_project.presentation.model.Checkout

data class CartState(
    val cartItems: List<CartItem>? = null,
    val checkout: Checkout? = null
)