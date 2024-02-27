package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class PhoneNumberValidatorUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String) : Boolean {
        val pattern = Regex("\\d{9}")

        return true
    }
}