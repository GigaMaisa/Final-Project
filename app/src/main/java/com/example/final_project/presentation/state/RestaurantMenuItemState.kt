package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.restaurant.MenuItemDetails

data class RestaurantMenuItemState(
    val menuItem: MenuItemDetails? = null,
    val errorMessage: Int? = null,
    val quantity: Int = 1,
    val totalPrice: Double? = null,
    val restaurantId: Int? = null,
    val category: String? = null
)
