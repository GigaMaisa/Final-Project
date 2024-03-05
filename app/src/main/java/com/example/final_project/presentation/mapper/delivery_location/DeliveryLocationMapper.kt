package com.example.final_project.presentation.mapper.delivery_location

import com.example.final_project.R
import com.example.final_project.domain.model.GetAddressType
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.model.GetLocationType
import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.delivery_location.LocationType

fun DeliveryLocation.toDomain() = GetDeliveryLocation(
    location = location,
    locationName = locationName,
    locationType = locationTypeConvertor(locationType),
    addressType = addressTypeConvertor(addressType),
    entrance = entrance,
    floor = floor,
    apartmentNumber = apartmentNumber,
    extraDescription = extraDescription
)

private fun locationTypeConvertor(locationType: LocationType): GetLocationType {
    return when(locationType.text) {
        R.string.house -> GetLocationType.HOUSE
        R.string.office -> GetLocationType.OFFICE
        R.string.apartment -> GetLocationType.APARTMENT
        R.string.other -> GetLocationType.OTHER
        else -> GetLocationType.APARTMENT
    }
}

private fun addressTypeConvertor(addressType: AddressType): GetAddressType {
    return when(addressType.text) {
        R.string.home_home -> GetAddressType.HOME
        R.string.work -> GetAddressType.WORK
        R.string.other -> GetAddressType.OTHER
        else -> GetAddressType.HOME
    }
}