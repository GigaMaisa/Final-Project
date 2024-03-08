package com.example.final_project.presentation.screen.delivery_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationsUseCase
import com.example.final_project.presentation.event.DeliveryLocationEvent
import com.example.final_project.presentation.mapper.delivery_location.toPresentation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryLocationViewModel @Inject constructor(getDeliveryLocationsUseCase: GetDeliveryLocationsUseCase): ViewModel() {

    val locations = getDeliveryLocationsUseCase().map { it.map { it.toPresentation() } }

    private val _uiState = MutableSharedFlow<DeliveryLocationUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: DeliveryLocationEvent) {
        when(event) {
            is DeliveryLocationEvent.NavigateToMapEvent -> navigateToMap()
        }
    }

    private fun navigateToMap() {
        viewModelScope.launch {
            _uiState.emit(DeliveryLocationUiEvent.NavigateToMap)
        }
    }


    sealed interface DeliveryLocationUiEvent {
        object NavigateToMap : DeliveryLocationUiEvent
    }
}