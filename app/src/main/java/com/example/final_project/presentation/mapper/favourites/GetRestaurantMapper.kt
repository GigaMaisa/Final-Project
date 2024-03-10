package com.example.final_project.presentation.mapper.favourites

import com.example.final_project.domain.model.Restaurant.GetRestaurant
import com.example.final_project.presentation.model.Restaurant

fun GetRestaurant.toPresentation() = Restaurant(
    restaurantId = restaurantId,
    title = title,
    type = type,
    deliveryFee = deliveryFee,
    deliveryTime = deliveryTime,
    image = image,
    rating = rating,
    isFavourite = isFavourite
)