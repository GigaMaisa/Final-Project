package com.example.final_project.presentation.mapper.location

import com.example.final_project.domain.model.location.GetLocationDelivery
import com.example.final_project.presentation.model.location.LocationDelivery

fun GetLocationDelivery.toPresentation() = LocationDelivery(
    isActive = active,
    latitude = latitude,
    longitude = longitude
)