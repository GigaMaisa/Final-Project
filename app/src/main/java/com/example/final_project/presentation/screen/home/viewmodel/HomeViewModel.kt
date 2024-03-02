package com.example.final_project.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.home.GetBannersUseCase
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.mapper.home.toPresentation
import com.example.final_project.presentation.state.HomeState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val getBannersUseCase: GetBannersUseCase) : ViewModel() {

    private val _homeStateFlow = MutableStateFlow(HomeState())
    val homeStateFlow = _homeStateFlow.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetBannersEvent -> getBanners()
            is HomeEvent.UpdateErrorMessageEvent -> updateErrorMessage(errorMessage = event.errorMessage)
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            getBannersUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _homeStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _homeStateFlow.update { currentState -> currentState.copy(banners = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _homeStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }
}