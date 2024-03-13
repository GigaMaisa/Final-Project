package com.example.final_project.data.remote.mapper.restaurant

import com.example.final_project.data.remote.model.restaurant.RestaurantMenuDto
import com.example.final_project.domain.model.restaurant.GetRestaurantMenu

fun RestaurantMenuDto.toDomain() = GetRestaurantMenu(
    menuCategory = menuCategory,
    items = items.map { it.toDomain() }
)