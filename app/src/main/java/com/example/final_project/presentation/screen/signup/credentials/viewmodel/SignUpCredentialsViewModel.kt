package com.example.final_project.presentation.screen.signup.credentials.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.signup.UserAdditionalDataUseCase
import com.example.final_project.domain.usecase.validators.EmailValidationUseCase
import com.example.final_project.domain.usecase.validators.FullNameValidatorUseCase
import com.example.final_project.domain.usecase.validators.PasswordValidatorUseCase
import com.example.final_project.presentation.event.signup.SendUserDataEvent
import com.example.final_project.presentation.mapper.toDomain
import com.example.final_project.presentation.model.AdditionalData
import com.example.final_project.presentation.state.AdditionalDataState
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
class SignUpCredentialsViewModel @Inject constructor(
    private val userAdditionalDataUseCase: UserAdditionalDataUseCase,
    private val emailValidationUseCase: EmailValidationUseCase,
    private val fullNameValidatorUseCase: FullNameValidatorUseCase,
    private val passwordValidatorUseCase: PasswordValidatorUseCase
): ViewModel() {
    private val _navigationEvent = MutableSharedFlow<SignUpCredentialsNavigationEvents>()
    val navigationEvent: SharedFlow<SignUpCredentialsNavigationEvents> get() = _navigationEvent

    private val _dataState = MutableStateFlow(AdditionalDataState())
    val dataState: SharedFlow<AdditionalDataState> = _dataState.asStateFlow()

    fun onUiEvent(event: SignUpCredentialsNavigationEvents) {
        when (event) {
            is SignUpCredentialsNavigationEvents.NavigateToSuccessPage -> {
                viewModelScope.launch {
                    _navigationEvent.emit(SignUpCredentialsNavigationEvents.NavigateToSuccessPage)
                }
            }
        }
    }

    fun onEvent(event: SendUserDataEvent) {
        when (event) {
            is SendUserDataEvent.SendUserData -> sendUserAdditionalData(
                AdditionalData(fullName = event.fullName, email = event.email, password = event.password)
            )
        }
    }

    private fun sendUserAdditionalData(additionalData: AdditionalData) {
        viewModelScope.launch {
            with(additionalData) {
                if (validateFields(fullName = fullName, email = email, password = password)) {
                    with(userAdditionalDataUseCase) {
                        addUserFullNameUseCase(additionalData.toDomain()).collect {
                            when (it) {
                                is Resource.Loading -> _dataState.update { currentState ->
                                    currentState.copy(isLoading = true)
                                }

                                is Resource.Success -> {
                                    addUserEmailUseCase(additionalData.toDomain()).collect {
                                        when (it) {
                                            is Resource.Loading -> _dataState.update { currentState ->
                                                currentState.copy(isLoading = true)
                                            }

                                            is Resource.Error -> updateErrorMessage(getErrorMessage(it.error))

                                            is Resource.Success -> {
                                                addUserPasswordUseCase(additionalData.toDomain()).collect {
                                                    when (it) {
                                                        is Resource.Loading -> _dataState.update { currentState ->
                                                            currentState.copy(isLoading = true)
                                                        }

                                                        is Resource.Success -> {
                                                            _dataState.update { currentState ->
                                                                currentState.copy(isLoading = false)
                                                            }

                                                            _navigationEvent.emit(SignUpCredentialsNavigationEvents.NavigateToSuccessPage)
                                                        }

                                                        is Resource.Error -> updateErrorMessage(getErrorMessage(it.error))
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }

                                is Resource.Error -> updateErrorMessage(getErrorMessage(it.error))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _dataState.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    private fun validateFields(fullName: String, email: String, password: String) : Boolean{
        return if (fullNameValidatorUseCase(fullName)) {
            if (emailValidationUseCase(email)) {
                passwordValidatorUseCase(password)
            } else {
                false
            }
        } else {
            false
        }
    }
}

sealed class SignUpCredentialsNavigationEvents {
    object NavigateToSuccessPage : SignUpCredentialsNavigationEvents()

}