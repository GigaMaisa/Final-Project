package com.example.final_project.presentation.mapper.home

import com.example.final_project.domain.model.Restaurant.GetRestaurant
import com.example.final_project.presentation.model.home.Restaurant

fun GetRestaurant.toPresentation() = Restaurant(
    restaurantId = restaurantId,
    image = image,
    title = title,
    type = type,
    deliveryTime = deliveryTime,
    rating = rating,
    deliveryFee = deliveryFee
)