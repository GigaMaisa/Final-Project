package com.example.final_project.presentation.screen.splash.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.firebase.GetAuthStateUseCase
import com.example.final_project.presentation.event.splash.SplashNavigationEvents
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val getAuthStateUseCase: GetAuthStateUseCase) : ViewModel() {
    private val _uiEvent = MutableStateFlow<SplashNavigationEvents>(SplashNavigationEvents.Pending)
    val uiEvent: StateFlow<SplashNavigationEvents> get() = _uiEvent

    init {
        readSession()
    }

    private fun readSession() {
        viewModelScope.launch {
            getAuthStateUseCase().collect {
                delay(1000)
                if (it)
                    _uiEvent.emit(SplashNavigationEvents.NavigateToHome)
                else
                    _uiEvent.emit(SplashNavigationEvents.NavigateToLogIn)
            }
        }
    }
}