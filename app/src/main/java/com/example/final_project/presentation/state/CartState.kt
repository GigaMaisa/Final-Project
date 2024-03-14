package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.model.cart.Checkout
import com.example.final_project.presentation.model.order.Order

data class CartState(
    val cartItems: List<Order>? = null,
    val checkout: Checkout? = null
)