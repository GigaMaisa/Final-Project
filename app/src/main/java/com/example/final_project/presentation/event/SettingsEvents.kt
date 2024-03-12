package com.example.final_project.presentation.event

sealed class SettingsEvents {
        class IsDarkModeChecked(val boolean: Boolean) : SettingsEvents()
        class ChangeLanguageEvent(val language: String) : SettingsEvents()
        object GetLanguageEvent : SettingsEvents()
        object GetDarkModeSettings: SettingsEvents()
    }