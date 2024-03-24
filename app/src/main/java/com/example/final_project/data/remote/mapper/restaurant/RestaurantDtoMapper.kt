package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.RestaurantDto
import com.example.final_project.domain.model.restaurant.GetRestaurant

fun RestaurantDto.toDomain() = GetRestaurant(
    restaurantId = restaurantId,
    image = image,
    title = title,
    type = type,
    deliveryTime = deliveryTime,
    rating = rating,
    deliveryFee = deliveryFee
)