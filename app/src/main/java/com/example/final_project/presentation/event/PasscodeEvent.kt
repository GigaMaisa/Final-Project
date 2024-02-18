package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.Passcode

sealed interface PasscodeEvent {
    class ChangeTextInputEvent(val passcode: Passcode) : PasscodeEvent
    object ResetPasscode : PasscodeEvent
}