package com.example.final_project.data.remote.mapper.route

import com.example.final_project.data.remote.model.DirectionsResponseDto
import com.example.final_project.domain.model.GetDirection

fun DirectionsResponseDto.toDomain() = GetDirection(
    routes = routes,
)