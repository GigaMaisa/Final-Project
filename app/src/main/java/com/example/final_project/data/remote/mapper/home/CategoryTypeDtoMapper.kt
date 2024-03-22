package com.example.final_project.data.remote.mapper.home

import com.example.final_project.data.remote.model.CategoryTypeDto
import com.example.final_project.domain.model.home.GetCategoryType

fun CategoryTypeDto.toDomain() = GetCategoryType(
    imageUrl = imageUrl,
    numberOfRestaurants = numberOfRestaurants,
    type = type
)