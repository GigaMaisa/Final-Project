package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.restaurant.Distance
import com.example.final_project.presentation.model.restaurant.Restaurant
import com.example.final_project.presentation.model.restaurant.RestaurantDetails

data class RestaurantDetailsState(
    val restaurantDetails: RestaurantDetails? = null,
    val isFavourite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val distance: Distance? = null,
    val favouriteRestaurant: Restaurant? = null,
    val defaultLocation: DeliveryLocation? = null
)
