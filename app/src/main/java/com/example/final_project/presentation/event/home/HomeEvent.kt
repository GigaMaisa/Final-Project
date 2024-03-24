package com.example.final_project.presentation.event.home

import com.example.final_project.presentation.model.restaurant.RestaurantType

sealed interface HomeEvent {
    object GetBannersEvent : HomeEvent
    object GetRestaurantsEvent : HomeEvent
    object GetFavouriteRestaurantsEvent : HomeEvent
    object GetCategoriesEvent : HomeEvent
    class UpdateErrorMessageEvent(val errorMessage: Int?) : HomeEvent
    class GoToAllRestaurantsEvent(
        val restaurantType: RestaurantType = RestaurantType.ALL,
        val searchFilter: String? = null
    ) : HomeEvent

    class GoToRestaurantDetailsEvent(val restaurantId: Int) : HomeEvent
}