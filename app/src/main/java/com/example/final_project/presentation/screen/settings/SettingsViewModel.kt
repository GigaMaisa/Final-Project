package com.example.final_project.presentation.screen.settings

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.datastore.GetLanguageUseCase
import com.example.final_project.domain.usecase.datastore.ReadDarkModeUseCase
import com.example.final_project.domain.usecase.datastore.SaveDarkModeUseCase
import com.example.final_project.domain.usecase.datastore.SaveLanguageUseCase
import com.example.final_project.presentation.event.SettingsEvents
import com.example.final_project.presentation.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val readDarkModeUseCase: ReadDarkModeUseCase,
    private val saveDarkModeUseCase: SaveDarkModeUseCase,
    private val getLanguageUseCase: GetLanguageUseCase,
    private val saveLanguageUseCase: SaveLanguageUseCase
) : ViewModel() {

    private val _settingsStateFlow = MutableStateFlow(SettingsState())
    val settingsStateFlow = _settingsStateFlow.asStateFlow()

    fun onEvent(event: SettingsEvents) {
        when (event) {
            is SettingsEvents.IsDarkModeChecked -> changeDarkMode(isDarkModeOn = event.boolean)
            is SettingsEvents.ChangeLanguageEvent -> saveLanguagePreference(language = event.language)
            is SettingsEvents.GetLanguageEvent -> getLanguageSetting()
            is SettingsEvents.GetDarkModeSettings -> getDarkModeSetting()
        }
    }

    private fun getLanguageSetting() {
        viewModelScope.launch {
            getLanguageUseCase().collect { language ->
                Log.d("RACXA ENA WAMOVIDA", language)

                _settingsStateFlow.update { currentState -> currentState.copy(language = language) }
            }
        }
    }

    private fun saveLanguagePreference(language: String) {
        Log.d("RACXA ENA DAISETA", language)

        viewModelScope.launch {
            saveLanguageUseCase(language = language)
            _settingsStateFlow.update { currentState -> currentState.copy(language = language) }
        }
    }

    private fun getDarkModeSetting() {
        viewModelScope.launch {
            readDarkModeUseCase().collect { isDarkMode ->
                _settingsStateFlow.update { currentState -> currentState.copy(isDarkMode = isDarkMode) }
            }
        }
    }

    private fun changeDarkMode(isDarkModeOn: Boolean) {
        viewModelScope.launch {
            saveDarkModeUseCase(isDarkModeOn = isDarkModeOn)
            _settingsStateFlow.update { currentState -> currentState.copy(isDarkMode = isDarkModeOn) }
        }
    }
}

