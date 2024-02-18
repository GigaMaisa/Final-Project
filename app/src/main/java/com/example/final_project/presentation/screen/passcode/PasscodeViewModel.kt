package com.example.final_project.presentation.screen.passcode

import androidx.lifecycle.ViewModel
import com.example.final_project.R
import com.example.final_project.presentation.event.PasscodeEvent
import com.example.final_project.presentation.model.Passcode
import com.example.final_project.presentation.state.PasscodeState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class PasscodeViewModel : ViewModel() {
    private val _passcodeStateFlow = MutableStateFlow(PasscodeState())
    val passcodeStateFlow = _passcodeStateFlow.asStateFlow()

    fun onEvent(event: PasscodeEvent) {
        when(event) {
            is PasscodeEvent.ChangeTextInputEvent -> changeTextInput(passcode = event.passcode)
            is PasscodeEvent.ResetPasscode -> resetPasscode()
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

            if (enteredPasscode == "567246") {
                _passcodeStateFlow.update { currentState -> currentState.copy(successMessage = R.string.dot_sms) }
            }else {
                _passcodeStateFlow.update { currentState -> currentState.copy(errorMessage = R.string.dot_email) }
            }
        }
    }
}