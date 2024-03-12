package com.example.final_project.presentation.screen.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.chatbot.UpdateAuthTokenUseCase
import com.example.final_project.domain.usecase.datastore.GetLanguageUseCase
import com.example.final_project.domain.usecase.datastore.ReadDarkModeUseCase
import com.example.final_project.domain.usecase.firebase.GetAuthStateUseCase
import com.example.final_project.presentation.event.splash.SplashNavigationEvents
import com.example.final_project.presentation.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val getAuthStateUseCase: GetAuthStateUseCase,
    private val updateAuthTokenUseCase: UpdateAuthTokenUseCase,
    private val readDarkModeUseCase: ReadDarkModeUseCase,
    private val readLanguageUseCase: GetLanguageUseCase
) : ViewModel() {

    private val _settingsStateFlow = MutableStateFlow(SettingsState())
    val settingsStateFlow = _settingsStateFlow.asStateFlow()

    private val _uiEvent = MutableStateFlow<SplashNavigationEvents>(SplashNavigationEvents.Pending)
    val uiEvent: StateFlow<SplashNavigationEvents> get() = _uiEvent

    init {
        readDarkModeState()
        readLanguageState()
        readSession()
    }

    private fun readDarkModeState() {
        viewModelScope.launch {
            readDarkModeUseCase().collect {
                _settingsStateFlow.update { currentState -> currentState.copy(isDarkMode = it) }
            }
        }
    }

    private fun readLanguageState() {
        viewModelScope.launch {
            readLanguageUseCase().collect {
                _settingsStateFlow.update { currentState -> currentState.copy(language = it) }
            }
        }
    }

    private fun readSession() {
        viewModelScope.launch {
            updateAuthTokenUseCase()
            getAuthStateUseCase().collect {
                delay(1000)
                if (it)
                    _uiEvent.emit(SplashNavigationEvents.NavigateToHome)
                else
                    _uiEvent.emit(SplashNavigationEvents.NavigateToLogIn)
            }
        }
    }
}