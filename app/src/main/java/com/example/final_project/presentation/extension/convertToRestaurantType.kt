package com.example.final_project.presentation.extension

import com.example.final_project.presentation.model.restaurant.RestaurantType

fun String.toRestaurantType() = when(this) {
    "Fast Food" -> RestaurantType.FAST_FOOD
    "Sushi" -> RestaurantType.SUSHI
    "Asian" -> RestaurantType.ASIAN
    "Georgian" -> RestaurantType.GEORGIAN
    "Pizza" -> RestaurantType.PIZZA
    else -> RestaurantType.ALL
}

fun RestaurantType.toCategory() = when(this) {
    RestaurantType.FAST_FOOD -> "Fast Food"
    RestaurantType.SUSHI -> "Sushi"
    RestaurantType.ASIAN -> "Asian"
    RestaurantType.GEORGIAN -> "Georgian"
    RestaurantType.PIZZA -> "Pizza"
    else -> ""
}