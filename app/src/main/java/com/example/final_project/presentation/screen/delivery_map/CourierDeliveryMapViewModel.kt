package com.example.final_project.presentation.screen.delivery_map

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationUseCase
import com.example.final_project.domain.usecase.route.GetDirectionUseCase
import com.example.final_project.presentation.mapper.delivery_location.toPresentation
import com.example.final_project.presentation.mapper.toPresentation
import com.example.final_project.presentation.state.CourierDeliveryState
import com.example.final_project.presentation.util.getErrorMessage
import com.google.android.gms.maps.model.LatLng
import com.google.firebase.Firebase
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CourierDeliveryMapViewModel @Inject constructor(
    private val getDirectionUseCase: GetDirectionUseCase,
    private val getDeliveryLocationUseCase: GetDeliveryLocationUseCase
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
                _directionsStateFlow.update { currentState -> currentState.copy(defaultLocation = it.toPresentation()) }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _directionsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    private fun getLocationUpdate() {
        Firebase.database.reference.child("deliveries").child("your_delivery_id")
            .addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val location = mutableMapOf<String?, Double?>()

                    snapshot.children.forEach {
                        location[it.key] = it.getValue(Double::class.java)
                    }

                    _directionsStateFlow.value.defaultLocation?.let {
                        getDirection(origin = it.location, LatLng(location["latitude"]!!, location["longitude"]!!))
                    }
                }
                override fun onCancelled(error: DatabaseError) {}
            })
    }
}