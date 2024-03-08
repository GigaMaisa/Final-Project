package com.example.final_project.presentation.event

sealed class ProfileNavigationUiEvents {
    object NavigateToLogIn : ProfileNavigationUiEvents()
    object NavigateToPayment : ProfileNavigationUiEvents()
}
