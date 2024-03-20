package com.example.final_project.presentation.event.restaurant

import com.example.final_project.presentation.model.restaurant.Restaurant

sealed class RestaurantDetailsEvent {
    class GetIfFavouriteEvent(val restaurantId: Int): RestaurantDetailsEvent()
    class AddFavouriteEvent(val restaurant: Restaurant): RestaurantDetailsEvent()
    class RemoverFavouriteEvent(val restaurant: Restaurant): RestaurantDetailsEvent()
    class GetRestaurantDetailsEvent(val restaurantId: Int): RestaurantDetailsEvent()
    class UpdateErrorMessageEvent(val errorMessage: Int?): RestaurantDetailsEvent()
    object UpdateFavouriteEvent : RestaurantDetailsEvent()

}
