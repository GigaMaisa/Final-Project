package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetMenuItemAdditions
import com.example.final_project.presentation.model.restaurant.MenuItemAdditions

fun GetMenuItemAdditions.toPresentation() = MenuItemAdditions(
    category = category,
    options = options.map { it.toPresentation() }
)