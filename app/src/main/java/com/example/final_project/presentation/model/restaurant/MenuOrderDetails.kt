package com.example.final_project.presentation.model.restaurant

data class MenuOrderDetails(
    val restaurantId: Int,
    val menuCategory: String,
    val menuItemDetails: MenuItemDetails
)