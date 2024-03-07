package com.example.final_project.domain.model

import com.example.final_project.data.remote.model.DirectionsResponseDto.Route

data class GetDirection(
    val routes: List<Route>,
)