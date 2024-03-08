package com.example.final_project.presentation.mapper.delivery_location

import com.example.final_project.R
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.delivery_location.LocationType

fun GetDeliveryLocation.toPresentation() = DeliveryLocation(
    location = location,
    locationName = locationName,
    addressType = convertAddressType(addressType),
    locationType = convertLocationType(locationType),
    entrance = entrance,
    floor = floor,
    apartmentNumber = apartmentNumber,
    extraDescription = extraDescription,
)

private fun convertAddressType(addressType: GetDeliveryLocation.GetAddressType): AddressType {
    return when(addressType) {
        GetDeliveryLocation.GetAddressType.HOME -> AddressType(
            R.string.home_home,
            R.drawable.ic_home_outline,
            R.drawable.ic_home_outline_selected,
        )
        GetDeliveryLocation.GetAddressType.WORK -> AddressType(
            R.string.work,
            R.drawable.ic_work_outline_black,
            R.drawable.ic_work_outline_selected
        )
        GetDeliveryLocation.GetAddressType.OTHER -> AddressType(
            R.string.other,
            R.drawable.ic_location_outline_black,
            R.drawable.ic_location_outline
        )
    }
}

private fun convertLocationType(locationType: GetDeliveryLocation.GetLocationType): LocationType {
    return when(locationType) {
        GetDeliveryLocation.GetLocationType.APARTMENT -> LocationType(R.drawable.ic_apartment, R.string.apartment)
        GetDeliveryLocation.GetLocationType.HOUSE -> LocationType(R.drawable.ic_house, R.string.house)
        GetDeliveryLocation.GetLocationType.OFFICE -> LocationType(R.drawable.ic_office, R.string.office)
        GetDeliveryLocation.GetLocationType.OTHER -> LocationType(R.drawable.ic_other, R.string.other)
    }
}