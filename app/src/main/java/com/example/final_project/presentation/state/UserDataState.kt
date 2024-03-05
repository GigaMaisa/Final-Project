package com.example.final_project.presentation.state

import com.example.final_project.presentation.model.UserData

data class UserDataState(
    val isLoading: Boolean = false,
    val userData: UserData? = null
)