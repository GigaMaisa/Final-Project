package com.example.final_project.data.remote.model.order

data class DeliveryLocationDto(
    val latitude: Double,
    val longitude: Double,
    val locationName: String,
    val locationType: String,
    val entrance: Int,
    val floor: Int,
    val apartmentNumber: Int,
    val extraDescription: String? = null
)