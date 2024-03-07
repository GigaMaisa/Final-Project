package com.example.final_project.data.remote.mapper.home

import com.example.final_project.data.remote.model.RestaurantDto
import com.example.final_project.domain.model.Restaurant.GetRestaurant

fun RestaurantDto.toDomain() = GetRestaurant(
    restaurantId = restaurantId,
    image = image,
    title = title,
    type = type,
     deliveryTime = deliveryTime,
    rating = rating,
    deliveryFee = deliveryFee
)