package com.example.final_project.presentation.extension

import com.example.final_project.domain.model.card.GetCard

fun List<GetCard>.decryptCardNumbers(decryptFunction: (String) -> String): List<GetCard> {
    return map { card ->
        val decryptedCardNumber = decryptFunction(card.cardNumber)
        card.copy(cardNumber = decryptedCardNumber)
    }
}
