package com.example.final_project.presentation.screen.reset_password.success.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.firebase.SignOutUseCase
import com.example.final_project.presentation.screen.signup.success.viewmodel.SignUpSuccessNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ResetSuccessViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
) : ViewModel() {
    private val _navigationEvent = MutableSharedFlow<ResetSuccessPageNavigationEvents>()
    val navigationEvent: SharedFlow<ResetSuccessPageNavigationEvents> get() = _navigationEvent

    fun onUiEvent(event: ResetSuccessPageNavigationEvents) {
        when(event) {
            is ResetSuccessPageNavigationEvents.NavigateToLogin -> {
                viewModelScope.launch {
                    signOutUseCase().collect {
                        when (it) {
                            is Resource.Success -> {
                                _navigationEvent.emit(ResetSuccessPageNavigationEvents.NavigateToLogin)
                            }
                            else  -> {}
                        }
                    }
                }
            }
        }
    }
}

sealed interface ResetSuccessPageNavigationEvents {
    object NavigateToLogin : ResetSuccessPageNavigationEvents
}