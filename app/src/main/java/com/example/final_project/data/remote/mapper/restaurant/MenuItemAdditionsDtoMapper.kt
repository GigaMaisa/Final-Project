package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.MenuItemAdditionsDto
import com.example.final_project.domain.model.restaurant.GetMenuItemAdditions

fun MenuItemAdditionsDto.toDomain() = GetMenuItemAdditions(
    category = category,
    options = options.options.map { it.toDomain() }
)