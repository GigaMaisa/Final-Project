package com.example.final_project.data.local.mapper.deliveryLocation

import com.example.final_project.data.local.model.AddressTypeEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity
import com.example.final_project.data.local.model.LocationTypeEntity
import com.example.final_project.domain.model.GetAddressType
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.model.GetLocationType
import com.google.android.gms.maps.model.LatLng

fun DeliveryLocationEntity.toDomain() = GetDeliveryLocation(
    location = LatLng(longitude, latitude),
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