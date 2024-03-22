package com.example.final_project.domain.usecase.delivery_location

import android.util.Log.d
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import javax.inject.Inject

class DeleteLocationUseCase @Inject constructor(private val deliveryLocationRepository: DeliveryLocationRepository) {
    suspend operator fun invoke(location: GetDeliveryLocation) = deliveryLocationRepository.deleteLocation(location).also {
        d("clickIsNotHappening3", location.toString())
    }
}