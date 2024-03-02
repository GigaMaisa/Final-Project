package com.example.final_project.presentation.event.home

sealed interface HomeEvent {
    object GetBannersEvent: HomeEvent
    class UpdateErrorMessageEvent(val errorMessage: Int?): HomeEvent
}