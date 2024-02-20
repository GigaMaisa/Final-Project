package com.example.final_project.presentation.model

data class CartItem(
    val id: Int,
    val cover: String,
    val title: String,
    val category: String,
    val price: Float,
    val quantity: Int,
    val deliveryFee: Float,
)
