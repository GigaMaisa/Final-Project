package com.example.final_project.presentation.mapper.home

import com.example.final_project.domain.model.home.GetCategoryType
import com.example.final_project.presentation.model.home.CategoryType

fun GetCategoryType.toPresentation() = CategoryType(
    imageUrl = imageUrl,
    numberOfRestaurants = numberOfRestaurants,
    type = type
)