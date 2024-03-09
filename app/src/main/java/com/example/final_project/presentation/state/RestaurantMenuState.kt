package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.RestaurantMenu

data class RestaurantMenuState(
    val menu: List<RestaurantMenu>? = null,
    val isFavourite: Boolean = false,
)
