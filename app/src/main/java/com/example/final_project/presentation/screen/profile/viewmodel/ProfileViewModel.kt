package com.example.final_project.presentation.screen.profile.viewmodel

import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.firebase.GetUserDataUseCase
import com.example.final_project.domain.usecase.firebase.RetrievePhotoUseCase
import com.example.final_project.domain.usecase.firebase.SignOutUseCase
import com.example.final_project.domain.usecase.firebase.UploadPhotoUseCase
import com.example.final_project.presentation.event.ProfileNavigationUiEvents
import com.example.final_project.presentation.mapper.toPresentation
import com.example.final_project.presentation.state.ProfileState
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
class ProfileViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase,
    private val getUserDataUseCase: GetUserDataUseCase,
    private val uploadPhotoUseCase: UploadPhotoUseCase,
    private val retrievePhotoUseCase: RetrievePhotoUseCase
): ViewModel() {

    private val _profileStateFlow = MutableStateFlow(ProfileState())
    val profileStateFlow = _profileStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileNavigationUiEvents>()
    val uiEvent: SharedFlow<ProfileNavigationUiEvents> get() = _uiEvent

    fun onEvent(event: ProfileEvent) {
        when (event) {
            is ProfileEvent.SignOutEvent -> logOut()
            is ProfileEvent.GetPhotoEvent -> getImage()
            is ProfileEvent.GetUserDataEvent -> getUserData()
            is ProfileEvent.UploadPhotoEvent -> uploadImage(imageUri = event.imageUri)
        }
    }

    fun onUiEvent(event: ProfileNavigationUiEvents) {
        when (event) {
            is ProfileNavigationUiEvents.NavigateToPayment -> {
                viewModelScope.launch {
                    _uiEvent.emit(ProfileNavigationUiEvents.NavigateToPayment)
                }
            }

            is ProfileNavigationUiEvents.NavigateToLocation -> viewModelScope.launch { _uiEvent.emit(ProfileNavigationUiEvents.NavigateToLocation) }
            is ProfileNavigationUiEvents.NavigateToLogIn -> viewModelScope.launch { _uiEvent.emit(ProfileNavigationUiEvents.NavigateToLogIn) }
            is ProfileNavigationUiEvents.NavigateToSettings -> viewModelScope.launch { _uiEvent.emit(ProfileNavigationUiEvents.NavigateToSettings) }
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        _profileStateFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _profileStateFlow.update { currentState ->
                            currentState.copy(userData = it.response.toPresentation(), isLoading = false)
                        }
                    }

                    is Resource.Error -> {}
                }
            }
        }
    }

    private fun getImage() {
        viewModelScope.launch {
            retrievePhotoUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        _profileStateFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        Log.d("RACXA VIEWMODEL", it.response)
                        _profileStateFlow.update { currentState ->
                            currentState.copy(imageRetrieve = it.response, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        Log.d("RACXA VIEWMODEL", "${it.error.errorCode}")

                        _profileStateFlow.update { currentState ->
                            currentState.copy(errorMessage = getErrorMessage(it.error), isLoading = false)
                        }
                    }
                }

            }
        }
    }

    private fun uploadImage(imageUri: Uri) {
        viewModelScope.launch {
            uploadPhotoUseCase(imageUri).collect {
                when (it) {
                    is Resource.Loading -> {
                        _profileStateFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        Log.d("RACXA VIEWMODEL", it.response)
                        _profileStateFlow.update { currentState ->
                            currentState.copy(imageUpload = it.response, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        Log.d("RACXA VIEWMODEL", "${it.error.errorCode}")

                        _profileStateFlow.update { currentState ->
                            currentState.copy(errorMessage = getErrorMessage(it.error), isLoading = false)
                        }
                    }
                }

            }
        }
    }

    private fun logOut() {
        viewModelScope.launch {
            signOutUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        _profileStateFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _uiEvent.emit(ProfileNavigationUiEvents.NavigateToLogIn)
                    }

                    is Resource.Error -> {
                        updateErrorMessage(getErrorMessage(it.error))
                    }
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _profileStateFlow.update { currentState ->
            currentState.copy(errorMessage = errorMessage, isLoading = false)
        }
    }

    sealed class ProfileEvent {
        data object SignOutEvent: ProfileEvent()
        data object GetUserDataEvent: ProfileEvent()
        data class UploadPhotoEvent(val imageUri: Uri): ProfileEvent()
        data object GetPhotoEvent: ProfileEvent()
    }
}