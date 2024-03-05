package com.example.final_project.presentation.screen.profile.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.datastore.GetLanguageUseCase
import com.example.final_project.domain.usecase.datastore.ReadDarkModeUseCase
import com.example.final_project.domain.usecase.datastore.SaveDarkModeUseCase
import com.example.final_project.domain.usecase.datastore.SaveLanguageUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val readDarkModeUseCase: ReadDarkModeUseCase,
    private val saveDarkModeUseCase: SaveDarkModeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {
    private val _darkModeFlow = MutableSharedFlow<Boolean>()
    val darkModeFlow: SharedFlow<Boolean> = _darkModeFlow.asSharedFlow()

    private val _languageFlow = MutableStateFlow("")
    val languageFlow: StateFlow<String> = _languageFlow.asStateFlow()

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.IsDarkModeChecked -> changeDarkMode(isDarkModeOn = event.boolean)
            is SettingsEvents.ChangeLanguageEvent -> saveLanguagePreference(language = event.language)
            is SettingsEvents.GetLanguageEvent -> getLanguageSetting()
        }
    }

    private fun getLanguageSetting() {
        viewModelScope.launch {
            getLanguageUseCase().collect { language ->
                Log.d("RACXA ENA WAMOVIDA", "$language")

                _languageFlow.value = language
            }
        }
    }

    private fun saveLanguagePreference(language: String) {
        Log.d("RACXA ENA DAISETA", "$language")

        viewModelScope.launch {
            saveLanguageUseCase(language = language)
        }
    }

    private fun getDarkModeSetting() {
        viewModelScope.launch {
            readDarkModeUseCase().collect { isDarkMode ->
                _darkModeFlow.emit(isDarkMode)
            }
        }
    }

    private fun changeDarkMode(isDarkModeOn: Boolean) {
        viewModelScope.launch {
            saveDarkModeUseCase(isDarkModeOn = isDarkModeOn)
        }
    }
}

sealed class SettingsEvents {
    data class IsDarkModeChecked(val boolean: Boolean) : SettingsEvents()
    data class ChangeLanguageEvent(val language: String) : SettingsEvents()
    data object GetLanguageEvent : SettingsEvents()
}