package com.example.final_project.presentation.model

import com.example.final_project.data.remote.model.DirectionsResponseDto

data class Direction(
    val routes: List<DirectionsResponseDto.Route>,
)
