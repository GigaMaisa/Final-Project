package com.example.final_project.presentation.event.home

sealed interface HomeEvent {
    object GetBannersEvent: HomeEvent
    object GetRestaurantsEvent: HomeEvent
    object GetFavouriteRestaurantsEvent: HomeEvent
    object GetCategoriesEvent: HomeEvent
    class UpdateErrorMessageEvent(val errorMessage: Int?): HomeEvent
}