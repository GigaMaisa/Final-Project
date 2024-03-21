package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class CardNameValidationUseCase @Inject constructor() {
    operator fun invoke(input: String): Boolean {
        return input.isNotEmpty()
    }
}