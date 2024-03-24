package com.example.final_project.domain.usecase.delivery_location

import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class GetDeliveryLocationsUseCase @Inject constructor(
    private val deliveryLocationRepository: DeliveryLocationRepository,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) {
    operator fun invoke(): Flow<List<GetDeliveryLocation>> {
        return deliveryLocationRepository.getLocations().flowOn(ioDispatcher)
    }
}