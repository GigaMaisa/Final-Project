package com.example.final_project.presentation.model

data class Restaurant(
    val restaurantId: Int,
    val title: String,
    val type: String,
    val deliveryFee: Int,
    val deliveryTime: String,
    val image: String,
    val rating: Double,
    val isFavourite: Boolean
)