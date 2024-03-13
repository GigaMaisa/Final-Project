package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetRestaurantDetails
import com.example.final_project.presentation.model.restaurant.RestaurantDetails

fun GetRestaurantDetails.toPresentation() = RestaurantDetails(
    restaurantId = restaurantId,
    deliveryFee = deliveryFee,
    description = description,
    image = image,
    latitude = latitude,
    longitude = longitude,
    rating = rating,
    restaurantName = restaurantName,
    type = type,
    menu = menu.map { it.toPresentation() }
)