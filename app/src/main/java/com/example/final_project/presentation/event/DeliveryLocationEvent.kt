package com.example.final_project.presentation.event

sealed class DeliveryLocationEvent {
    object NavigateToMapEvent : DeliveryLocationEvent()
    object NavigateBackEvent: DeliveryLocationEvent()
}
