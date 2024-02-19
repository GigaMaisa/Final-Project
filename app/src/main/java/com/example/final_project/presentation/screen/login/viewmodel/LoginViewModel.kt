package com.example.final_project.presentation.screen.login.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<LoginFragmentUiEvents>()
    val navigationEvent: SharedFlow<LoginFragmentUiEvents> get() = _navigationEvent

    fun onUiEvent(events: LoginFragmentUiEvents) {
        when (events) {
            is LoginFragmentUiEvents.NavigateToHomePage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(LoginFragmentUiEvents.NavigateToHomePage)
                }
            }

            is LoginFragmentUiEvents.NavigateToSignUpPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(LoginFragmentUiEvents.NavigateToSignUpPage)
                }
            }

            is LoginFragmentUiEvents.NavigateToSmsAuthPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(LoginFragmentUiEvents.NavigateToSmsAuthPage(events.phoneNumber))
                }
            }

            is LoginFragmentUiEvents.ForgotPassword -> {
                viewModelScope.launch {
                    _navigationEvent.emit(LoginFragmentUiEvents.ForgotPassword)
                }
            }
        }
    }
}

sealed class LoginFragmentUiEvents {
    object NavigateToHomePage : LoginFragmentUiEvents()
    object NavigateToSignUpPage : LoginFragmentUiEvents()
    object ForgotPassword : LoginFragmentUiEvents()
    data class NavigateToSmsAuthPage(val phoneNumber: String) : LoginFragmentUiEvents()
}