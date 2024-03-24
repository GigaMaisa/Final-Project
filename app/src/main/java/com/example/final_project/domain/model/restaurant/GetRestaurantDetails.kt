package com.example.final_project.domain.model.restaurant

data class GetRestaurantDetails(
    val restaurantId: Int,
    val deliveryFee: Int?,
    val description: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    val restaurantName: String,
    val type: String,
    val menu: List<GetRestaurantMenu>
)
