package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.LocationType
import com.google.android.gms.maps.model.LatLng

sealed class DeliveryLocationAddEvent {
    class SelectAddressTypeEvent(val addressType: AddressType): DeliveryLocationAddEvent()
    class AddLocationEvent(val buildingName: String,
                           val entrance: String,
                           val floor: String,
                           val apartment: String,
                           val isDefault: Boolean,
                           val description: String,
                           val locationType: LocationType,
                           val addressLocationName: String,
                           val addressLocation: LatLng) : DeliveryLocationAddEvent()
    class UpdateErrorMessage(val errorMessage: Int?): DeliveryLocationAddEvent()
}