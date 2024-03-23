package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.Direction
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.google.android.gms.maps.model.LatLng

data class CourierDeliveryState(
    val direction: Direction? = null,
    val defaultLocation: DeliveryLocation? = null,
    val errorMessage: Int? = null,
    val courierLocation: LatLng? = null
)
