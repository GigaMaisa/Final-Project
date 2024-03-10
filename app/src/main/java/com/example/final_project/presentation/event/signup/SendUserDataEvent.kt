package com.example.final_project.presentation.event.signup

import com.example.final_project.presentation.model.ErrorType

sealed class SendUserDataEvent {
    data class SendUserData(val email: String, val password: String, val fullName: String) : SendUserDataEvent()
    class UpdateErrorMessage(val message: Int?, val errorType: ErrorType): SendUserDataEvent()
}