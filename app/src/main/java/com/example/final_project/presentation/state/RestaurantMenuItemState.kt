package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.restaurant.MenuItemDetails

data class RestaurantMenuItemState(
    val menuItem: MenuItemDetails? = null,
    val errorMessage: Int? = null
)
