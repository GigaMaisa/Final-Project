package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.MenuItemDetailsDto
import com.example.final_project.domain.model.restaurant.GetMenuItemDetails

fun MenuItemDetailsDto.toDomain() = GetMenuItemDetails(
    description = description,
    id = id,
    image = image,
    name = name,
    price = price,
    menuItemAdditions = menuItemAdditions.map { it.toDomain() }
)