package com.example.final_project.presentation.screen.passcode.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.R
import com.example.final_project.presentation.event.PasscodeEvent
import com.example.final_project.presentation.model.Passcode
import com.example.final_project.presentation.state.PasscodeState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PasscodeViewModel @Inject constructor() : ViewModel() {
    private val _passcodeStateFlow = MutableStateFlow(PasscodeState())
    val passcodeStateFlow = _passcodeStateFlow.asStateFlow()

    private val _navigationEvent = MutableSharedFlow<PasscodeNavigationEvents>()
    val navigationEvent: SharedFlow<PasscodeNavigationEvents> get() = _navigationEvent

    fun onEvent(event: PasscodeEvent) {
        when(event) {
            is PasscodeEvent.ChangeTextInputEvent -> changeTextInput(passcode = event.passcode)
            is PasscodeEvent.ResetPasscode -> resetPasscode()
        }
    }

    fun onUiEvent(events: PasscodeNavigationEvents) {
        when (events) {
            is PasscodeNavigationEvents.NavigateBack -> {
                viewModelScope.launch {
                    _navigationEvent.emit(PasscodeNavigationEvents.NavigateBack)
                }
            }

            is PasscodeNavigationEvents.NavigateToSignUpCredentialsPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(
                        PasscodeNavigationEvents.NavigateToSignUpCredentialsPage(
                            events.phoneNumber
                        )
                    )
                }
            }
        }
    }

    private fun resetPasscode() {
        _passcodeStateFlow.update { currentState -> currentState.copy(passcode = (1..6).map { Passcode(it) }) }
    }

    private fun changeTextInput(passcode: Passcode) {
        _passcodeStateFlow.update { currentState -> currentState.copy(errorMessage = null) }
        val newPasscode = _passcodeStateFlow.value.passcode.map {
            if (it.id == passcode.id) {
                it.copy(currentNumber = passcode.currentNumber)
            }else {
                it
            }}

        _passcodeStateFlow.update { currentState -> currentState.copy(passcode = newPasscode) }

        if (passcode.id == 6) {
            val enteredPasscode = _passcodeStateFlow.value.passcode
                .mapNotNull { it.currentNumber?.toString() }
                .reduceOrNull { acc, s -> acc + s }

            if (enteredPasscode == "999999") {
                _passcodeStateFlow.update { currentState -> currentState.copy(successMessage = R.string.dot_sms) }
            }else {
                _passcodeStateFlow.update { currentState -> currentState.copy(errorMessage = R.string.dot_email) }
            }
        }
    }

    sealed class PasscodeNavigationEvents {
        object NavigateBack : PasscodeNavigationEvents()
        data class NavigateToSignUpCredentialsPage(val phoneNumber: String?) : PasscodeNavigationEvents()
    }
}