package com.example.final_project.presentation.event.signup

import android.app.Activity

sealed class SendSmsEvent {
    data class SendSmsToProvidedNumber(val phoneNumber: String, val activity: Activity) : SendSmsEvent()
}