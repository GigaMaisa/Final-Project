package com.example.final_project.presentation.state

data class ResetPasswordState(
    val isLoading: Boolean = false,
    val errorMessage: Int? = null,
)