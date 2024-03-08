package com.example.final_project.domain.repository.delivery_location

import com.example.final_project.domain.model.GetDeliveryLocation
import kotlinx.coroutines.flow.Flow

interface DeliveryLocationRepository {
    fun getLocations(): Flow<List<GetDeliveryLocation>>

    suspend fun addLocation(location: GetDeliveryLocation)
}