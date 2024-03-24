package com.example.final_project.data.remote.mapper.location

import com.example.final_project.data.remote.model.delivery.LocationDeliveryDto
import com.example.final_project.domain.model.location.GetLocationDelivery

fun LocationDeliveryDto.toDomain() = GetLocationDelivery(
    active = active ?: false,
    latitude = latitude ?: 0.0,
    longitude = longitude ?: 0.0
)