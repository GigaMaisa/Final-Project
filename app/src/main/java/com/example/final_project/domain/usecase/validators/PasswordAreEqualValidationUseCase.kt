package com.example.final_project.domain.usecase.validators

import javax.inject.Inject

class PasswordAreEqualValidationUseCase @Inject constructor() {
    operator fun invoke(passwordOne: String, passwordTwo: String) : Boolean {
        return passwordOne == passwordTwo
    }
}