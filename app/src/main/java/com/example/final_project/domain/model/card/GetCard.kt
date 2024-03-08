package com.example.final_project.domain.model.card

data class GetCard(
    val id: Int,
    val cardNumber: String,
    val expirationDate: String,
    val cvv: String,
    val cardName: String
)
