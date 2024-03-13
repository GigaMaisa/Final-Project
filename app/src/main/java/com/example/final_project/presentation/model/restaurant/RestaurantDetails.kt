package com.example.final_project.presentation.model.restaurant

data class RestaurantDetails(
    val restaurantId: Int,
    val deliveryFee: Double?,
    val description: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val restaurantName: String,
    val type: String,
    val menu: List<RestaurantMenu>
)
