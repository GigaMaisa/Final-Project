package com.example.final_project.presentation.screen.delivery_location_add

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.R
import com.example.final_project.domain.usecase.delivery_location.AddDeliveryLocationUseCase
import com.example.final_project.domain.usecase.delivery_location.UpdateDefaultToFalseUseCase
import com.example.final_project.domain.usecase.validators.EmptyFieldsValidationUseCase
import com.example.final_project.domain.usecase.validators.EmptyNumberFieldsValidationUseCase
import com.example.final_project.presentation.event.DeliveryLocationAddEvent
import com.example.final_project.presentation.mapper.delivery_location.toDomain
import com.example.final_project.presentation.model.delivery_location.AddressType
import com.example.final_project.presentation.model.delivery_location.DeliveryLocation
import com.example.final_project.presentation.model.delivery_location.LocationType
import com.example.final_project.presentation.state.DeliveryLocationState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DeliveryLocationAddViewModel @Inject constructor(
    private val emptyNumberFieldsValidationUseCase: EmptyNumberFieldsValidationUseCase,
    private val emptyFieldsValidationUseCase: EmptyFieldsValidationUseCase,
    private val addDeliveryLocationUseCase: AddDeliveryLocationUseCase,
    private val updateDefaultToFalseUseCase: UpdateDefaultToFalseUseCase
) : ViewModel() {
    private val _deliveryLocationStateFlow = MutableStateFlow(DeliveryLocationState())
    val deliveryLocationStateFlow = _deliveryLocationStateFlow.asStateFlow()

    private val _uiState = MutableSharedFlow<DeliveryLocationAddUiEvent>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: DeliveryLocationAddEvent) {
        when (event) {
            is DeliveryLocationAddEvent.SelectAddressTypeEvent -> selectAddressType(address = event.addressType)
            is DeliveryLocationAddEvent.AddLocationEvent -> with(event) {
                addLocation(buildingName, entrance, floor, apartment, description, locationType, addressLocationName, addressLocation, isDefault)

            }
            is DeliveryLocationAddEvent.UpdateErrorMessage -> updateErrorMessage(errorMessage = event.errorMessage)
        }
    }

    private fun selectAddressType(address: AddressType) {
        val addressTypes = _deliveryLocationStateFlow.value.addressType.map {
            if (it == address) {
                it.copy(isSelected = true)
            } else
                it.copy(isSelected = false)
        }
        _deliveryLocationStateFlow.update { currentState -> currentState.copy(addressType = addressTypes) }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _deliveryLocationStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    private fun addLocation(buildingName: String, entrance: String, floor: String, apartment: String, description: String, locationType: LocationType, addressLocationName: String, addressLocation: LatLng, isDefault: Boolean) {
        viewModelScope.launch {
            if (!emptyFieldsValidationUseCase(buildingName, entrance, floor, apartment)) {
                updateErrorMessage(R.string.fill_all_fields)
            } else if (!emptyNumberFieldsValidationUseCase(entrance, floor, apartment)) {
                updateErrorMessage(R.string.fill_number_fields)
            } else {
                val address = _deliveryLocationStateFlow.value.addressType.find { it.isSelected }
                val selectedLocation = DeliveryLocation(
                    location = addressLocation,
                    locationName = addressLocationName,
                    locationType = locationType,
                    addressType = address!!,
                    entrance = entrance.toInt(),
                    floor = floor.toInt(),
                    apartmentNumber = apartment.toInt(),
                    extraDescription = description,
                    isDefault = isDefault
                )
                updateDefaultToFalseUseCase()
                addDeliveryLocationUseCase(selectedLocation.toDomain())
                _uiState.emit(DeliveryLocationAddUiEvent.NavigateToDeliveryLocations)
            }
        }
    }

    sealed interface DeliveryLocationAddUiEvent {
        object NavigateToDeliveryLocations : DeliveryLocationAddUiEvent
        object NavigateBack: DeliveryLocationAddUiEvent
    }
}