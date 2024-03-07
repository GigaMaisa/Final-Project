package com.example.final_project.presentation.screen.delivery_map

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.route.GetDirectionUseCase
import com.example.final_project.presentation.mapper.toPresentation
import com.example.final_project.presentation.state.CourierDeliveryState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourierDeliveryMapViewModel @Inject constructor(private val getDirectionUseCase: GetDirectionUseCase) : ViewModel() {
    private val _directionsStateFlow = MutableStateFlow(CourierDeliveryState())
    val directionStateFlow = _directionsStateFlow.asStateFlow()

    fun getDirection(origin: LatLng, destination: LatLng) {
        viewModelScope.launch {
            getDirectionUseCase(origin = origin, destination = destination).collect {resource ->
                d("ResourceResponse", resource.toString())
                when(resource) {
                    is Resource.Loading -> {}
                    is Resource.Error -> {}
                    is Resource.Success -> _directionsStateFlow.update { currentState -> currentState.copy(direction = resource.response.toPresentation()) }
                }
            }
        }
    }
}