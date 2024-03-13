package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.MenuItemAdditionsOptionsDto
import com.example.final_project.domain.model.restaurant.GetMenuItemAdditionsOptions

fun MenuItemAdditionsOptionsDto.toDomain() = GetMenuItemAdditionsOptions(
    name = name,
    selected = selected
)