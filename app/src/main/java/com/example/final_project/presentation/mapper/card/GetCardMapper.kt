package com.example.final_project.presentation.mapper.card

import com.example.final_project.domain.model.card.GetCard
import com.example.final_project.presentation.model.card.Card

fun GetCard.toPresentation() : Card {
    return Card(id = id, cardNumber = cardNumber, expirationDate = expirationDate, cvv = cvv, cardName = cardName)
}