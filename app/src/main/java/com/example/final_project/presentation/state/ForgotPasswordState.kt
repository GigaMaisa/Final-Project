package com.example.final_project.presentation.state

data class ForgotPasswordState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
    val verificationId: String? = null
)