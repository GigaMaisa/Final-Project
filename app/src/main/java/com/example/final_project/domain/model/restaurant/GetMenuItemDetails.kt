package com.example.final_project.domain.model.restaurant

data class GetMenuItemDetails(
    val description: String,
    val id: String,
    val image: String,
    val name: String,
    val price: Double,
    val menuItemAdditions: List<GetMenuItemAdditions>
)
