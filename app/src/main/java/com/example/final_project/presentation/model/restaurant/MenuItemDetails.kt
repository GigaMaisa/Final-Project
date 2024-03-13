package com.example.final_project.presentation.model.restaurant

data class MenuItemDetails(
    val description: String,
    val id: String,
    val image: String,
    val name: String,
    val price: Double,
    val menuItemAdditions: List<MenuItemAdditions>

)
