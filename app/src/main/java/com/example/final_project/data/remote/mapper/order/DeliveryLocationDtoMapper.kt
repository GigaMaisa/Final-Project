package com.example.final_project.data.remote.mapper.order

import com.example.final_project.data.remote.model.order.DeliveryLocationDto
import com.example.final_project.domain.model.GetDeliveryLocation

fun GetDeliveryLocation.toDto() = DeliveryLocationDto(
    latitude = location.latitude,
    longitude = location.longitude,
    locationName = locationName,
    locationType = locationType.name,
    entrance = entrance,
    floor = floor,
    apartmentNumber = apartmentNumber,
    extraDescription = extraDescription
)