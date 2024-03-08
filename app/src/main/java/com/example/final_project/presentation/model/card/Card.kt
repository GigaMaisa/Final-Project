package com.example.final_project.presentation.model.card

data class Card(
    val id: Int,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val cardName: String
)