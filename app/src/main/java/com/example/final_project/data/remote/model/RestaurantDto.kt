package com.example.final_project.data.remote.model

data class RestaurantDto(
    val restaurantId: Int,
    val image: String,
    val title: String,
    val type: String,
    val deliveryTime: String,
    val rating: Double,
    val deliveryFee: Int
)
