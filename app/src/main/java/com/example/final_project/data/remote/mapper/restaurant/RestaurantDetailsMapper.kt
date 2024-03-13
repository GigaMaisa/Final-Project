package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.RestaurantDetailsDto
import com.example.final_project.domain.model.restaurant.GetRestaurantDetails

fun RestaurantDetailsDto.toDomain() = GetRestaurantDetails(
    restaurantId = restaurantId,
    deliveryFee = deliveryFee,
    description = description,
    image = image,
    latitude = latitude,
    longitude = longitude,
    rating = rating,
    restaurantName = restaurantName,
    type = type,
    menu = menu.map { it.toDomain() }
)