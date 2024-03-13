package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.restaurant.GetMenuItemDetails
import com.example.final_project.presentation.model.restaurant.MenuItemDetails

fun GetMenuItemDetails.toPresentation() = MenuItemDetails(
    description = description,
    id = id,
    image = image,
    name = name,
    price = price,
    menuItemAdditions = menuItemAdditions.map { it.toPresentation() }
)