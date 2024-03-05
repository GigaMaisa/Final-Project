package com.example.final_project.presentation.state

import com.example.final_project.R
import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation

data class DeliveryLocationState(
    val deliveryLocation: DeliveryLocation? = null,
    val addressType: List<AddressType> = listOf(
        AddressType(R.string.home_home, R.drawable.ic_home_outline, R.drawable.ic_home_outline_selected, true),
        AddressType(R.string.work, R.drawable.ic_work_outline_black, R.drawable.ic_work_outline_selected),
        AddressType(R.string.other, R.drawable.ic_location_outline_black, R.drawable.ic_location_outline),
    ),
    val errorMessage: Int? = null
)
