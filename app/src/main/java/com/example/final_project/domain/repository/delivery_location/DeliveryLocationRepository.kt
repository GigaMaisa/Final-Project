package com.example.final_project.domain.repository.delivery_location

import com.example.final_project.domain.model.GetDeliveryLocation
import kotlinx.coroutines.flow.Flow

interface DeliveryLocationRepository {
    fun getLocations(): Flow<List<GetDeliveryLocation>>

    fun getLocation(): Flow<GetDeliveryLocation?>

    suspend fun updateLocation(location: GetDeliveryLocation)

    suspend fun deleteLocation(location: GetDeliveryLocation)

    suspend fun updateDefaultToFalse()

    suspend fun addLocation(location: GetDeliveryLocation)
}