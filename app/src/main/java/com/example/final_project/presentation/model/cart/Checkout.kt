package com.example.final_project.presentation.model.cart

data class Checkout(
    val id: Int = 0,
    val subTotal: Double,
    val chargeTotal: Double = 0.0
)