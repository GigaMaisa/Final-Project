package com.example.final_project.presentation.model.cart

data class CartItem(
    val id: Int,
    val cover: String,
    val title: String,
    val category: String,
    val price: Double,
    val quantity: Int,
    val deliveryFee: Double,
)
