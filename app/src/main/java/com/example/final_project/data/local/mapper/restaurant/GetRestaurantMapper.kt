package com.example.final_project.data.local.mapper.restaurant

import com.example.final_project.data.local.model.RestaurantEntity
import com.example.final_project.domain.model.Restaurant.GetRestaurant

fun GetRestaurant.toData() = RestaurantEntity(
    restaurantId = restaurantId,
    title = title,
    type = type,
    deliveryFee = deliveryFee,
    deliveryTime = deliveryTime,
    image = image,
    rating = rating
)