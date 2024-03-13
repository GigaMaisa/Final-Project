package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetRestaurant
import com.example.final_project.presentation.model.restaurant.Restaurant

fun Restaurant.toDomain() = GetRestaurant(
    restaurantId = restaurantId,
    title = title,
    type = type,
    deliveryFee = deliveryFee,
    deliveryTime = deliveryTime,
    image = image,
    rating = rating,
    isFavourite = isFavourite
)