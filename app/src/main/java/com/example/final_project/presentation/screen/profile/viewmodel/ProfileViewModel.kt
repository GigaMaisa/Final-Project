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
import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.screen.signup.credentials.viewmodel.SignUpCredentialsNavigationEvents
import com.example.final_project.presentation.state.PhotoState
import com.example.final_project.presentation.state.ProfileState
import com.example.final_project.presentation.state.SignOutState
import com.example.final_project.presentation.state.UserDataState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
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
    val cartItems = listOf(
        CartItem(1, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 50.0, 2, 3.5),
        CartItem(2, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 4, 3.5),
        CartItem(4, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 5, 3.5),
        CartItem(5, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(6, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 6, 3.5),
        CartItem(7, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(8, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 2, 3.5),
    )

    private val _profileStateFlow = MutableStateFlow(ProfileState())
    val profileStateFlow = _profileStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<ProfileNavigationUiEvents>()
    val uiEvent: SharedFlow<ProfileNavigationUiEvents> get() = _uiEvent

    private val _signOutState = MutableStateFlow(SignOutState())
    val signOutState: StateFlow<SignOutState> get() = _signOutState

    private val _imageUploadStatus = MutableStateFlow(PhotoState())
    val imageUploadStatus: StateFlow<PhotoState> = _imageUploadStatus

    private val _userImage = MutableStateFlow(PhotoState())
    val userImageFlow: StateFlow<PhotoState> = _userImage

    private val _userDataFlow = MutableStateFlow(UserDataState())
    val userDataFlow = _userDataFlow.asStateFlow()

    init {
        _profileStateFlow.update { currentState -> currentState.copy(favourites = cartItems) }
    }

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

            else -> {}
        }
    }

    private fun getUserData() {
        viewModelScope.launch {
            getUserDataUseCase().collect {
                when (it) {
                    is Resource.Loading -> {
                        _userDataFlow.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        _userDataFlow.update { currentState ->
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
                        _userImage.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        Log.d("RACXA VIEWMODEL", it.response)
                        _userImage.update { currentState ->
                            currentState.copy(imageUri = it.response, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        Log.d("RACXA VIEWMODEL", "${it.error.errorCode}")

                        _userImage.update { currentState ->
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
                        _imageUploadStatus.update { currentState ->
                            currentState.copy(isLoading = true)
                        }
                    }

                    is Resource.Success -> {
                        Log.d("RACXA VIEWMODEL", it.response)
                        _imageUploadStatus.update { currentState ->
                            currentState.copy(imageUri = it.response, isLoading = false)
                        }
                    }

                    is Resource.Error -> {
                        Log.d("RACXA VIEWMODEL", "${it.error.errorCode}")

                        _imageUploadStatus.update { currentState ->
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
                        _signOutState.update { currentState ->
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
        _signOutState.update { currentState ->
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