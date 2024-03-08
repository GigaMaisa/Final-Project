package com.example.final_project.data.local.mapper.deliveryLocation

import com.example.final_project.data.local.model.DeliveryLocationEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity.AddressTypeEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity.LocationTypeEntity
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.model.GetDeliveryLocation.GetAddressType
import com.example.final_project.domain.model.GetDeliveryLocation.GetLocationType

fun GetDeliveryLocation.toData() = DeliveryLocationEntity(
    longitude = location.longitude,
    latitude = location.latitude,
    locationName = locationName,
    locationType = locationTypeConvertor(locationType),
    addressType = addressTypeConvertor(addressType),
    entrance = entrance,
    floor = floor,
    apartmentNumber = apartmentNumber,
    extraDescription = extraDescription
)

private fun locationTypeConvertor(locationType: GetLocationType): LocationTypeEntity {
    return when (locationType) {
        GetLocationType.HOUSE -> LocationTypeEntity.HOUSE
        GetLocationType.OFFICE -> LocationTypeEntity.OFFICE
        GetLocationType.APARTMENT -> LocationTypeEntity.APARTMENT
        GetLocationType.OTHER -> LocationTypeEntity.OTHER
    }
}

private fun addressTypeConvertor(addressType: GetAddressType): AddressTypeEntity {
    return when (addressType) {
        GetAddressType.HOME -> AddressTypeEntity.HOME
        GetAddressType.WORK -> AddressTypeEntity.WORK
        GetAddressType.OTHER -> AddressTypeEntity.OTHER
    }
}