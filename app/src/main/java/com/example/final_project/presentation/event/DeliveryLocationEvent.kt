package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.delivery_location.DeliveryLocation

sealed class DeliveryLocationEvent {
    object NavigateToMapEvent : DeliveryLocationEvent()
    object NavigateBackEvent: DeliveryLocationEvent()
    class DeleteLocationEvent(val location: DeliveryLocation): DeliveryLocationEvent()
    class UpdateDefaultLocationEvent(val location: DeliveryLocation): DeliveryLocationEvent()

}
