package com.example.final_project.data.local.mapper.deliveryLocation

import com.example.final_project.data.local.model.DeliveryLocationEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity.LocationTypeEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity.AddressTypeEntity
import com.example.final_project.domain.model.GetDeliveryLocation.GetAddressType
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.model.GetDeliveryLocation.GetLocationType
import com.google.android.gms.maps.model.LatLng

fun DeliveryLocationEntity.toDomain() = GetDeliveryLocation(
    location = LatLng(latitude, longitude),
    locationName = locationName,
    locationType = locationTypeConvertor(locationType),
    addressType = addressTypeConvertor(addressType),
    entrance = entrance,
    floor = floor,
    apartmentNumber = apartmentNumber,
    extraDescription = extraDescription
)

private fun locationTypeConvertor(locationTypeEntity: LocationTypeEntity): GetLocationType {
    return when(locationTypeEntity) {
        LocationTypeEntity.HOUSE -> GetLocationType.HOUSE
        LocationTypeEntity.OFFICE -> GetLocationType.OFFICE
        LocationTypeEntity.APARTMENT -> GetLocationType.APARTMENT
        LocationTypeEntity.OTHER -> GetLocationType.OTHER
    }
}

private fun addressTypeConvertor(addressTypeEntity: AddressTypeEntity): GetAddressType {
    return when(addressTypeEntity) {
        AddressTypeEntity.HOME -> GetAddressType.HOME
        AddressTypeEntity.WORK -> GetAddressType.WORK
        AddressTypeEntity.OTHER -> GetAddressType.OTHER
    }
}