package com.example.final_project.domain.usecase.delivery_location

import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import javax.inject.Inject

class UpdateLocationUseCase @Inject constructor(private val deliveryLocationRepository: DeliveryLocationRepository) {
    suspend operator fun invoke(location: GetDeliveryLocation) = deliveryLocationRepository.updateLocation(location)
}