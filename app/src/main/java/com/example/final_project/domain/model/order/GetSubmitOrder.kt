package com.example.final_project.domain.model.order

import com.example.final_project.domain.model.GetDeliveryLocation

data class GetSubmitOrder(
    val isActive: Boolean,
    val location: GetDeliveryLocation,
    val menu: List<GetOrder>,
    val totalPrice: Double
)
