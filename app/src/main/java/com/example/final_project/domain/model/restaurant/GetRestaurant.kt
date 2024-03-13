package com.example.final_project.domain.model.restaurant

data class GetRestaurant(
    val restaurantId: Int,
    val image: String,
    val title: String,
    val type: String,
    val deliveryTime: String,
    val rating: Double,
    val deliveryFee: Int,
    val isFavourite: Boolean = false
)