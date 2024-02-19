package com.example.final_project.presentation.model

data class RestaurantMenu(
    val id: Int,
    val cover: String,
    val title: String,
    val category: String,
    val deliveryPrice: String,
    val deliveryTime: String,
    val rating: String
)