package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetRestaurantMenu
import com.example.final_project.presentation.model.restaurant.RestaurantMenu

fun GetRestaurantMenu.toPresentation() = RestaurantMenu(
    menuCategory = menuCategory,
    items = items.map { it.toPresentation() }
)