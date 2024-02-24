package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class PhoneNumberValidatorUseCase @Inject constructor() {
    operator fun invoke(phoneNumber: String) : Boolean {
        val regex = Regex("^\\+995\\d{9}$")
        return regex.matches(phoneNumber)
    }
}