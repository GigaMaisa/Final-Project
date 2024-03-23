package com.example.final_project.domain.usecase.location

import com.example.final_project.domain.repository.location.LocationDeliveryRepository
import javax.inject.Inject

class GetCourierLocationUpdateUseCase @Inject constructor(private val locationDeliveryRepository: LocationDeliveryRepository) {
    suspend operator fun invoke() = locationDeliveryRepository.getCourierLocation()
}