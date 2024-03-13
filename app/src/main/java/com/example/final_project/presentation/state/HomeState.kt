package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.home.Banner
import com.example.final_project.presentation.model.restaurant.Restaurant

data class HomeState(
    val banners: List<Banner>? = null,
    val restaurants: List<Restaurant>? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
