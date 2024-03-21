package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class CardCvvValidationUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        val cvvRegex = "^[0-9]{3}$".toRegex()

        return input.matches(cvvRegex)
    }
}