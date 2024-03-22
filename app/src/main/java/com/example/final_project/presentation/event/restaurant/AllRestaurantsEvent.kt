package com.example.final_project.presentation.event.restaurant

import com.example.final_project.presentation.model.restaurant.RestaurantType

sealed class AllRestaurantsEvent {
    object GoBackEvent: AllRestaurantsEvent()
    object GetAllRestaurantsEvent: AllRestaurantsEvent()
    object GetFavouriteRestaurants: AllRestaurantsEvent()
    class GoToRestaurantsDetailsEvent(val restaurantId: Int): AllRestaurantsEvent()
    class UpdateErrorMessageEvent(val message: Int?): AllRestaurantsEvent()
    class GetRestaurantByCategoryEvent(val restaurantType: RestaurantType): AllRestaurantsEvent()
}
