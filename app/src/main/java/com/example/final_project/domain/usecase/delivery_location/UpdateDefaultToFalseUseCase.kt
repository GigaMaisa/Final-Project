package com.example.final_project.domain.usecase.delivery_location

import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import javax.inject.Inject

class UpdateDefaultToFalseUseCase @Inject constructor(private val deliveryLocationRepository: DeliveryLocationRepository) {
    suspend operator fun invoke() = deliveryLocationRepository.updateDefaultToFalse()
}