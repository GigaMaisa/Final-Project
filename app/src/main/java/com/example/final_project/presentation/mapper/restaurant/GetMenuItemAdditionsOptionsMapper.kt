package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetMenuItemAdditionsOptions
import com.example.final_project.presentation.model.restaurant.MenuItemAdditionsOptions

fun GetMenuItemAdditionsOptions.toPresentation() = MenuItemAdditionsOptions(
    name = name,
    selected = selected
)