package com.example.final_project.data.remote.model.order

data class SubmitOrderDto(
    val userUuid: String? = null,
    val active: Boolean,
    val location: DeliveryLocationDto,
    val menu: List<OrderDto>,
    val totalPrice: Double,
    val fullName: String? = null
)
