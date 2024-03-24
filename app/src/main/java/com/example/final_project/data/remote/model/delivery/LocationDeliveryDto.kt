package com.example.final_project.data.remote.model.delivery

data class LocationDeliveryDto(
    val active: Boolean?,
    val latitude: Double?,
    val longitude: Double?
) {
    constructor(): this(null, null, null)
}
