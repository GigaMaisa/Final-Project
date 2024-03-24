package com.example.final_project.presentation.screen.delivery_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationUseCase
import com.example.final_project.domain.usecase.location.GetCourierLocationUpdateUseCase
import com.example.final_project.domain.usecase.route.GetDirectionUseCase
import com.example.final_project.presentation.mapper.delivery_location.toPresentation
import com.example.final_project.presentation.mapper.location.toPresentationModel
import com.example.final_project.presentation.mapper.toPresentation
import com.example.final_project.presentation.state.CourierDeliveryState
import com.example.final_project.presentation.util.getErrorMessage
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourierDeliveryMapViewModel @Inject constructor(
    private val getDirectionUseCase: GetDirectionUseCase,
    private val getDeliveryLocationUseCase: GetDeliveryLocationUseCase,
    private val getCourierLocationUpdateUseCase: GetCourierLocationUpdateUseCase
) : ViewModel() {
    private val _directionsStateFlow = MutableStateFlow(CourierDeliveryState())
    val directionStateFlow = _directionsStateFlow.asStateFlow()

    init {
        getDefaultLocation()
        getLocationUpdate()
    }

    private fun getDirection(origin: LatLng, destination: LatLng) {
        viewModelScope.launch {
            getDirectionUseCase(origin = origin, destination = destination).collect {resource ->
                when(resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> _directionsStateFlow.update { currentState -> currentState.copy(direction = resource.response.toPresentation()) }
                }
            }
        }
    }

    private fun getDefaultLocation() {
        viewModelScope.launch {
            getDeliveryLocationUseCase().collect {
                _directionsStateFlow.update { currentState -> currentState.copy(defaultLocation = it?.toPresentation()) }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _directionsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    private fun getLocationUpdate() {
        viewModelScope.launch {
            getCourierLocationUpdateUseCase().collect {resource->
                when(resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                    is Resource.Success -> {
                        _directionsStateFlow.update { currentState -> currentState.copy(courierLocation = resource.response.toPresentationModel()) }
                        _directionsStateFlow.value.defaultLocation?.let {
                            getDirection(origin = it.location, LatLng(resource.response.latitude, resource.response.longitude))
                        }
                    }
                }
            }
        }
    }
}