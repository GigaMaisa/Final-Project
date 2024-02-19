package com.example.final_project.presentation.screen.signup.credentials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.presentation.screen.signup.start.viewmodel.SignUpNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpCredentialsViewModel @Inject constructor(): ViewModel() {
    private val _navigationEvent = MutableSharedFlow<SignUpCredentialsNavigationEvents>()
    val navigationEvent: SharedFlow<SignUpCredentialsNavigationEvents> get() = _navigationEvent

    fun onUiEvent(event: SignUpCredentialsNavigationEvents) {
        when (event) {
            is SignUpCredentialsNavigationEvents.NavigateToSuccessPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpCredentialsNavigationEvents.NavigateToSuccessPage)
                }
            }
        }
    }
}

sealed class SignUpCredentialsNavigationEvents {
    object NavigateToSuccessPage : SignUpCredentialsNavigationEvents()
}