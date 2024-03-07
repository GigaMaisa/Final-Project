package com.example.final_project.presentation.mapper

import com.example.final_project.domain.model.GetDirection
import com.example.final_project.presentation.model.Direction

fun GetDirection.toPresentation() = Direction(
    routes = routes
)