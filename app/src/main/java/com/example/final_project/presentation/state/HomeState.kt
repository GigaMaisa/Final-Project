package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.home.Banner

data class HomeState(
    val banners: List<Banner>? = null,
    val isLoading: Boolean = false,
    val errorMessage: Int? = null
)
