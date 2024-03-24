package com.example.final_project.presentation.event.forgot_pass

import com.google.firebase.auth.PhoneAuthOptions

sealed class ForgotPasswordEvents {
    data class SendVerificationToEmail(val email: String) : ForgotPasswordEvents()
    data class SendVerificationToPhoneNumber(val phoneNumber: String, val options: PhoneAuthOptions.Builder) : ForgotPasswordEvents()
    object GoBackEvent: ForgotPasswordEvents()
}
