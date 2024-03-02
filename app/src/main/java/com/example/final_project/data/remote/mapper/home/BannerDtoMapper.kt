package com.example.final_project.data.remote.mapper.home

import com.example.final_project.data.remote.model.BannerDto
import com.example.final_project.domain.model.home.GetBanner

fun BannerDto.toDomain() = GetBanner(
    restaurantId = restaurantId,
    title = title,
    image = image
)