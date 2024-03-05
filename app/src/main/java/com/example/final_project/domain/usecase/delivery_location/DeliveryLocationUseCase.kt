package com.example.final_project.domain.usecase.delivery_location

import com.example.final_project.domain.usecase.validators.EmptyFieldsValidationUseCase
import com.example.final_project.domain.usecase.validators.EmptyNumberFieldsValidationUseCase
import javax.inject.Inject

data class DeliveryLocationUseCase@Inject constructor(
    val getDeliveryLocationsUseCase: GetDeliveryLocationsUseCase,
    val addDeliveryLocationUseCase: AddDeliveryLocationUseCase,
    val emptyFieldsValidationUseCase: EmptyFieldsValidationUseCase,
    val emptyNumberFieldsValidationUseCase: EmptyNumberFieldsValidationUseCase
)
