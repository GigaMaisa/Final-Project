package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class EmptyNumberFieldsValidationUseCase @Inject constructor() {
    operator fun invoke(vararg integerFields: String): Boolean {
        return integerFields.all { it.toIntOrNull() != null }
    }
}