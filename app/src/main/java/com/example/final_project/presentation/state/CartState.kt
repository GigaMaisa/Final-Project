package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.cart.Checkout
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.order.Order

data class CartState(
    val cartItems: List<Order>? = null,
    val checkout: Checkout? = null,
    val deliveryLocation: DeliveryLocation? = null
)