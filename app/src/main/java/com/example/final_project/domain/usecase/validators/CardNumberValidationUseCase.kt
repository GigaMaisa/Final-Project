package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class CardNumberValidationUseCase @Inject constructor() {
    operator fun invoke(cardNumber: String): Boolean {
        val card = cardNumber.replace("\\s".toRegex(), "")

        if (card.length != 16) {
            return false
        }

        return true
    }
}