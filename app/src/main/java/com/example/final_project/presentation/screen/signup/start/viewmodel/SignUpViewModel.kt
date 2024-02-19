package com.example.final_project.presentation.screen.signup.start.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.presentation.screen.welcome.viewmodel.WelcomeUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor() : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvents>()
    val navigationEvent: SharedFlow<SignUpNavigationEvents> get() = _navigationEvent

    fun onUiEvent(events: SignUpNavigationEvents) {
        when (events) {
            is SignUpNavigationEvents.NavigateToSignInPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpNavigationEvents.NavigateToSignInPage)
                }
            }

            is SignUpNavigationEvents.NavigateToSmsAuthPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpNavigationEvents.NavigateToSmsAuthPage(events.phoneNumber))
                }
            }
        }
    }
}

sealed class SignUpNavigationEvents {
    object NavigateToSignInPage : SignUpNavigationEvents()
    data class NavigateToSmsAuthPage(val phoneNumber: String) : SignUpNavigationEvents()
}