package com.example.final_project.data.remote.model.restaurant

data class RestaurantMenuDto(
    val menuCategory: String,
    val items: List<MenuItemDetailsDto>
)
