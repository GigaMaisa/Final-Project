package com.example.final_project.presentation.screen.delivery_location

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.delivery_location.DeleteLocationUseCase
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationsUseCase
import com.example.final_project.domain.usecase.delivery_location.UpdateDefaultToFalseUseCase
import com.example.final_project.domain.usecase.delivery_location.UpdateLocationUseCase
import com.example.final_project.presentation.event.DeliveryLocationEvent
import com.example.final_project.presentation.mapper.delivery_location.toDomain
import com.example.final_project.presentation.mapper.delivery_location.toPresentation
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryLocationViewModel @Inject constructor(
    getDeliveryLocationsUseCase: GetDeliveryLocationsUseCase,
    private val deleteLocationUseCase: DeleteLocationUseCase,
    private val updateLocationUseCase: UpdateLocationUseCase,
    private val updateDefaultToFalseUseCase: UpdateDefaultToFalseUseCase
) : ViewModel() {

    val locations = getDeliveryLocationsUseCase().map { it.map { it.toPresentation() } }

    private val _uiState = MutableSharedFlow<DeliveryLocationUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: DeliveryLocationEvent) {
        when(event) {
            is DeliveryLocationEvent.NavigateToMapEvent -> navigateToMap()
            is DeliveryLocationEvent.NavigateBackEvent -> navigateBack()
            is DeliveryLocationEvent.DeleteLocationEvent -> deleteLocation(location = event.location)
            is DeliveryLocationEvent.UpdateDefaultLocationEvent -> updateDefaultLocation(location = event.location)
        }
    }

    private fun navigateToMap() {
        viewModelScope.launch {
            _uiState.emit(DeliveryLocationUiEvent.NavigateToMap)
        }
    }

    private fun navigateBack() {
        viewModelScope.launch {
            _uiState.emit(DeliveryLocationUiEvent.NavigateBack)
        }
    }

    private fun deleteLocation(location: DeliveryLocation) {
        viewModelScope.launch {
            deleteLocationUseCase(location = location.toDomain())
        }
    }

    private fun updateDefaultLocation(location: DeliveryLocation) {
        viewModelScope.launch {
            updateDefaultToFalseUseCase()
            updateLocationUseCase(location = location.copy(isDefault = true).toDomain())
        }
    }


    sealed interface DeliveryLocationUiEvent {
        object NavigateToMap : DeliveryLocationUiEvent
        object NavigateBack: DeliveryLocationUiEvent
    }
}