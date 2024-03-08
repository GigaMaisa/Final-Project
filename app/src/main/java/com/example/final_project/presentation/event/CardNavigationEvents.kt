package com.example.final_project.presentation.event

sealed class CardNavigationEvents {
    object NavigateBack : CardNavigationEvents()
    object NavigateToAddNewCard : CardNavigationEvents()
}