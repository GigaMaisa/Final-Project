package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.Restaurant

sealed class RestaurantDetailsEvent {
    class GetIfFavouriteEvent(val restaurantId: Int): RestaurantDetailsEvent()
    class AddFavouriteEvent(val restaurant: Restaurant): RestaurantDetailsEvent()
    class RemoverFavouriteEvent(val restaurant: Restaurant): RestaurantDetailsEvent()
}
