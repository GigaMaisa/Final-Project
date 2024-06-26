package com.example.final_project.presentation.model.delivery_location

import com.google.android.gms.maps.model.LatLng

data class DeliveryLocation(
    val id: Int? = null,
    val location: LatLng,
    val locationName: String,
    val locationType: LocationType,
    val addressType: AddressType,
    val entrance: Int,
    val floor: Int,
    val apartmentNumber: Int,
    val isDefault: Boolean,
    val extraDescription: String? = null
)

