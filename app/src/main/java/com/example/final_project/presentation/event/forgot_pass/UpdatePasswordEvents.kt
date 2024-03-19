package com.example.final_project.presentation.event.forgot_pass

sealed class UpdatePasswordEvents {
    data class UpdatePassword(val passwordOne: String, val passwordTwo: String) : UpdatePasswordEvents()
}