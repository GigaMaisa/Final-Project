package com.example.final_project.presentation.event.signup

sealed class SendUserDataEvent {
    data class SendUserData(val email: String, val password: String, val fullName: String) : SendUserDataEvent()
}