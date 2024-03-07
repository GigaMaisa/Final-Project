package com.example.final_project.domain.model.Restaurant

data class GetRestaurant(
    val restaurantId: Int,
    val image: String,
    val title: String,
    val type: String,
    val deliveryTime: String,
    val rating: Double,
    val deliveryFee: Int
)