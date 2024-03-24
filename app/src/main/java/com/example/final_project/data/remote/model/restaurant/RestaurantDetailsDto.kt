package com.example.final_project.data.remote.model.restaurant

import com.squareup.moshi.Json

data class RestaurantDetailsDto(
    val restaurantId: Int,
    val deliveryFee: Int?,
    val description: String,
    val image: String,
    val latitude: Double,
    val longitude: Double,
    val rating: Double,
    @Json(name = "restaurant_name") val restaurantName: String,
    val type: String,
    val menu: List<RestaurantMenuDto>
)