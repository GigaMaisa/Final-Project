package com.example.final_project.presentation.mapper.home

import com.example.final_project.domain.model.home.GetBanner
import com.example.final_project.presentation.model.home.Banner

fun GetBanner.toPresentation() = Banner(
    restaurantId = restaurantId,
    title = title,
    image = image
)