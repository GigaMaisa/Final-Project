package com.example.final_project.presentation.screen.signup.start.viewmodel

import android.app.Activity
import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.signup.SendVerificationCodeUseCase
import com.example.final_project.domain.usecase.validators.PhoneNumberValidatorUseCase
import com.example.final_project.presentation.event.signup.SendSmsEvent
import com.example.final_project.presentation.state.VerificationState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(
    private val sendVerificationCodeUseCase: SendVerificationCodeUseCase,
    private val phoneNumberValidatorUseCase: PhoneNumberValidatorUseCase
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<SignUpNavigationEvents>()
    val navigationEvent: SharedFlow<SignUpNavigationEvents> get() = _navigationEvent

    private val _verificationState = MutableStateFlow(VerificationState())

    fun onUiEvent(events: SignUpNavigationEvents) {
        when (events) {
            is SignUpNavigationEvents.NavigateToSignInPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpNavigationEvents.NavigateToSignInPage)
                }
            }

            is SignUpNavigationEvents.NavigateToSmsAuthPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpNavigationEvents.NavigateToSmsAuthPage(events.phoneNumber, events.verificationId))
                }
            }
        }
    }

    fun onEvent(event: SendSmsEvent) {
        when (event) {
            is SendSmsEvent.SendSmsToProvidedNumber -> sendVerificationCodeToPhoneNumber(event.phoneNumber, event.activity)
        }
    }

    private fun sendVerificationCodeToPhoneNumber(phoneNumber: String, activity: Activity) {
        viewModelScope.launch {
            if (phoneNumberValidatorUseCase(phoneNumber)) {
                sendVerificationCodeUseCase(phoneNumber, activity).collect { resource ->
                    when (resource) {
                        is Resource.Loading -> _verificationState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            _verificationState.update { currentState ->
                                currentState.copy(data = resource.response)
                            }

                            d("fuchuriko", resource.response)
                            _navigationEvent.emit(
                                SignUpNavigationEvents.NavigateToSmsAuthPage(
                                    phoneNumber = phoneNumber,
                                    verificationId = resource.response
                                )
                            )
                        }

                        is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _verificationState.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }
}

sealed class SignUpNavigationEvents {
    data object NavigateToSignInPage : SignUpNavigationEvents()
    data class NavigateToSmsAuthPage(val phoneNumber: String, val verificationId: String) : SignUpNavigationEvents()
}