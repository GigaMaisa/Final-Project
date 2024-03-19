package com.example.final_project.presentation.model.cart

import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.order.Order

data class SubmitOrder(
    val isActive: Boolean,
    val location: DeliveryLocation,
    val menu: List<Order>,
    val totalPrice: Double
)