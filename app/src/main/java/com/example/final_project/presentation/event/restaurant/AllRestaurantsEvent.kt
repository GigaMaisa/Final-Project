package com.example.final_project.presentation.event.restaurant

sealed class AllRestaurantsEvent {
    object GoBackEvent: AllRestaurantsEvent()
    object GetAllRestaurantsEvent: AllRestaurantsEvent()
    class GoToRestaurantsDetailsEvent(val restaurantId: Int): AllRestaurantsEvent()
    class UpdateErrorMessageEvent(val message: Int?): AllRestaurantsEvent()
}
