package com.example.final_project.presentation.event

sealed interface ProfileNavigationUiEvents {
    object NavigateToLogIn : ProfileNavigationUiEvents
    object NavigateToPayment : ProfileNavigationUiEvents
    object NavigateToLocation: ProfileNavigationUiEvents
    object NavigateToSettings: ProfileNavigationUiEvents
}
