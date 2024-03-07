package com.example.final_project.presentation.model.home

data class Restaurant(
    val restaurantId: Int,
    val image: String,
    val title: String,
    val type: String,
    val deliveryTime: String,
    val rating: Double,
    val deliveryFee: Int
)
