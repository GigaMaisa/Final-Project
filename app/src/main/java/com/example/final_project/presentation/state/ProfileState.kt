package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.Profile
import com.example.final_project.presentation.model.cart.CartItem

data class ProfileState(
    val profile: Profile? = null,
    val favourites: List<CartItem>? = null
)
