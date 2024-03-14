package com.example.final_project.presentation.model.cart

import com.example.final_project.presentation.model.order.Order


data class CartCheckout(
    val id: String,
    val cartItem: Order? = null,
    val checkout: Checkout? = null
)