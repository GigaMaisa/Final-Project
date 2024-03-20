package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.presentation.model.restaurant.Restaurant
import com.example.final_project.presentation.model.restaurant.RestaurantDetails

fun RestaurantDetails.toRestaurant() = Restaurant(
    restaurantId = restaurantId,
    image = image,
    title = restaurantName,
    type = type,
    deliveryTime = "",
    deliveryFee = deliveryFee?.toInt() ?: 0,
    rating = rating,
    isFavourite = true
)