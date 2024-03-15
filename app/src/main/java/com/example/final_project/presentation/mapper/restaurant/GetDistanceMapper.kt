package com.example.final_project.presentation.mapper.restaurant

import com.example.final_project.domain.model.distance.GetDistance
import com.example.final_project.presentation.model.restaurant.Distance

fun GetDistance.toPresentation() = Distance(
    distance = distance,
    duration = duration
)