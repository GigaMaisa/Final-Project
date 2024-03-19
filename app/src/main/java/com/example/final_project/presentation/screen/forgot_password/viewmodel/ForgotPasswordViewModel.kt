package com.example.final_project.presentation.screen.forgot_password.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.R
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.forgot_pass.ResetPasswordViaEmailUseCase
import com.example.final_project.domain.usecase.signup.SendVerificationCodeUseCase
import com.example.final_project.domain.usecase.validators.EmailValidationUseCase
import com.example.final_project.domain.usecase.validators.PhoneNumberValidatorUseCase
import com.example.final_project.presentation.event.forgot_pass.ForgotPasswordEvents
import com.example.final_project.presentation.model.ErrorType
import com.example.final_project.presentation.screen.login.viewmodel.LoginFragmentUiEvents
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsNavigationEvents
import com.example.final_project.presentation.state.AdditionalDataState
import com.example.final_project.presentation.state.ForgotPasswordState
import com.example.final_project.presentation.util.getErrorMessage
import com.google.firebase.auth.PhoneAuthOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ForgotPasswordViewModel @Inject constructor(
    private val resetPasswordViaEmailUseCase: ResetPasswordViaEmailUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase,
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,

    ) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<ForgotPasswordNavigationEvents>()
    val navigationEvent: SharedFlow<ForgotPasswordNavigationEvents> get() = _navigationEvent

    private val _forgotPassFlow = MutableStateFlow(ForgotPasswordState())
    val forgotPassFlow: StateFlow<ForgotPasswordState> = _forgotPassFlow.asStateFlow()

    fun onEvent(events: ForgotPasswordEvents) {
        when (events) {
            is ForgotPasswordEvents.SendVerificationToEmail -> sendVerificationToEmail(events.email)
            is ForgotPasswordEvents.SendVerificationToPhoneNumber -> sendCodeToPhoneNumber(
                phoneNumber = events.phoneNumber,
                options = events.options
            )
        }
    }

    private fun sendVerificationToEmail(email: String) {
        viewModelScope.launch {
            if (validateEmail(email)) {
                resetPasswordViaEmailUseCase(email).collect {
                    when (it) {
                        is Resource.Loading -> _forgotPassFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }

                        is Resource.Error -> _forgotPassFlow.update { currentState ->
                            currentState.copy(errorMessage = getErrorMessage(it.error), isLoading = false)
                        }

                        is Resource.Success -> _navigationEvent.emit(ForgotPasswordNavigationEvents.NavigateToRestorePasswordPage)
                    }
                }
            }
        }
    }

    private fun sendCodeToPhoneNumber(phoneNumber: String, options: PhoneAuthOptions.Builder) {
        viewModelScope.launch {
            if (validatePhoneNumber(phoneNumber)) {
                sendVerificationCodeUseCase(phoneNumber, options).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _forgotPassFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            _forgotPassFlow.update { currentState ->
                                currentState.copy(verificationId = resource.response)
                            }
                            _navigationEvent.emit(ForgotPasswordNavigationEvents.NavigateToPasscodeFragment(resource.response))
                        }

                        else -> {}
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?, errorType: ErrorType) {
        when(errorType) {
            ErrorType.EMAIL -> _forgotPassFlow.update { currentState ->
                currentState.copy(errorMessage = errorMessage, isLoading = false)
            }

            ErrorType.PHONE -> _forgotPassFlow.update { currentState ->
                currentState.copy(errorMessage = errorMessage, isLoading = false)
            }

            else -> {}
        }
    }

    private fun validatePhoneNumber(phoneNumber: String): Boolean {
        return if (!phoneNumberValidatorUseCase(phoneNumber)) {
            updateErrorMessage(R.string.phone_validation_error, ErrorType.PHONE)
            false
        }else
            true
    }

    private fun validateEmail(email: String): Boolean {
        if (!emailValidationUseCase(email)) {
            updateErrorMessage(R.string.email_validation_error, ErrorType.EMAIL)
            return false
        }
        return true
    }

    sealed class ForgotPasswordNavigationEvents {
        object NavigateToRestorePasswordPage : ForgotPasswordNavigationEvents()
        data class NavigateToPasscodeFragment(val verificationId: String) : ForgotPasswordNavigationEvents()
    }
}