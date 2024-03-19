package com.example.final_project.presentation.screen.reset_password.password.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.R
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.GetUserAdditionalData
import com.example.final_project.domain.usecase.signup.AddUserPasswordUseCase
import com.example.final_project.domain.usecase.validators.PasswordAreEqualValidationUseCase
import com.example.final_project.domain.usecase.validators.PasswordValidatorUseCase
import com.example.final_project.presentation.event.forgot_pass.UpdatePasswordEvents
import com.example.final_project.presentation.model.ErrorType
import com.example.final_project.presentation.state.ResetPasswordState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetPasswordViewModel @Inject constructor(
    private val addUserPasswordUseCase: AddUserPasswordUseCase,
    private val passwordsAreEqualValidationUseCase: PasswordAreEqualValidationUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<ResetPasswordNavigationEvents>()
    val navigationEvent: SharedFlow<ResetPasswordNavigationEvents> get() = _navigationEvent

    private val _resetPasswordState = MutableStateFlow(ResetPasswordState())
    val resetPasswordState: SharedFlow<ResetPasswordState> = _resetPasswordState.asStateFlow()

    fun onEvent(event: UpdatePasswordEvents) {
        when (event) {
            is UpdatePasswordEvents.UpdatePassword -> {
                updatePassword(event.passwordOne, event.passwordTwo)
            }
        }
    }

    fun onUiEvent(event: ResetPasswordNavigationEvents) {
        when (event) {
            is ResetPasswordNavigationEvents.NavigateToSuccessResetPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ResetPasswordNavigationEvents.NavigateToSuccessResetPage)
                }
            }

            is ResetPasswordNavigationEvents.NavigateBack -> {
                viewModelScope.launch {
                    _navigationEvent.emit(ResetPasswordNavigationEvents.NavigateBack)
                }
            }
        }
    }

    private fun updatePassword(passwordOne: String, passwordTwo: String) {
        viewModelScope.launch {
            if (validateFields(passwordOne = passwordOne, passwordTwo = passwordTwo)) {
                addUserPasswordUseCase(
                    GetUserAdditionalData(
                        fullName = "",
                        email = "",
                        password = passwordOne
                    )
                ).collect {
                    when (it) {
                        is Resource.Loading -> _resetPasswordState.update { currentState ->
                            currentState.copy(isLoading = true)
                        }

                        is Resource.Success -> {
                            _resetPasswordState.update { currentState ->
                                currentState.copy(isLoading = false)
                            }
                            _navigationEvent.emit(ResetPasswordNavigationEvents.NavigateToSuccessResetPage)
                        }

                        else -> {}
                    }
                }
            }
        }
    }


    private fun validateFields(passwordOne: String, passwordTwo: String): Boolean {
        if (!passwordValidatorUseCase(passwordOne)) {
            updateErrorMessage(R.string.password_validation_error, ErrorType.PASSWORD)
            return false
        } else if (!passwordsAreEqualValidationUseCase(passwordOne, passwordTwo)) {
            updateErrorMessage(R.string.passwords_dont_match, ErrorType.PASSWORDS_NOT_MATCHING)
            return false
        }
        return true
    }

    private fun updateErrorMessage(errorMessage: Int?, errorType: ErrorType) {
        when(errorType) {
            ErrorType.PASSWORD -> _resetPasswordState.update { currentState ->
                currentState.copy(errorMessage = errorMessage, isLoading = false)
            }

            ErrorType.PASSWORDS_NOT_MATCHING -> _resetPasswordState.update { currentState ->
                currentState.copy(errorMessage = errorMessage, isLoading = false)
            }

            else -> {

            }
        }
    }
}

sealed interface ResetPasswordNavigationEvents {
    object NavigateToSuccessResetPage: ResetPasswordNavigationEvents
    object NavigateBack: ResetPasswordNavigationEvents
}