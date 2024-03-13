package com.example.final_project.domain.model.restaurant

data class GetRestaurantMenu(
    val menuCategory: String,
    val items: List<GetMenuItemDetails>
)
