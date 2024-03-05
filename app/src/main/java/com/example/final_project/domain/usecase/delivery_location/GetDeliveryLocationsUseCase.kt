package com.example.final_project.domain.usecase.delivery_location

import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GetDeliveryLocationsUseCase @Inject constructor(private val deliveryLocationRepository: DeliveryLocationRepository) {
    suspend operator fun invoke() = withContext(IO) {
        deliveryLocationRepository.getLocations()
    }
}