package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.restaurant.RestaurantDetails

data class RestaurantDetailsState(
    val restaurantDetails: RestaurantDetails? = null,
    val isFavourite: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
