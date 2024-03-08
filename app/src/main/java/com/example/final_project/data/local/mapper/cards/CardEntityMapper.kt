package com.example.final_project.data.local.mapper.cards

import com.example.final_project.data.local.model.CardEntity
import com.example.final_project.domain.model.card.GetCard

fun CardEntity.toDomain() : GetCard {
    return GetCard(
        id = id,
        cardNumber = cardNumber,
        expirationDate = expirationDate,
        cvv = cvv,
        cardName = cardName
    )
}