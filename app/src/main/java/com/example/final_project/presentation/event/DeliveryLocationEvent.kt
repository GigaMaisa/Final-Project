package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.LocationType
import com.google.android.gms.maps.model.LatLng

sealed class DeliveryLocationEvent {
    class SelectAddressTypeEvent(val addressType: AddressType): DeliveryLocationEvent()
    class AddLocationEvent(val buildingName: String,
                           val entrance: String,
                           val floor: String,
                           val apartment: String,
                           val description: String,
                           val locationType: LocationType,
                           val addressLocationName: String,
                           val addressLocation: LatLng) : DeliveryLocationEvent()
    class UpdateErrorMessage(val errorMessage: Int?): DeliveryLocationEvent()
}