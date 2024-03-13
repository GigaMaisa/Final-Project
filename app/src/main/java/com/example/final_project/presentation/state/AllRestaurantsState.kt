package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.restaurant.Restaurant

data class AllRestaurantsState(
    val restaurants: List<Restaurant>? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)