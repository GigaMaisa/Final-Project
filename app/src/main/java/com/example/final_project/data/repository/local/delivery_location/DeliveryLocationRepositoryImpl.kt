package com.example.final_project.data.repository.local.delivery_location

import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.mapper.deliveryLocation.toData
import com.example.final_project.data.local.mapper.deliveryLocation.toDomain
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DeliveryLocationRepositoryImpl @Inject constructor(private val deliveryLocationDao: DeliveryLocationDao): DeliveryLocationRepository {
    override suspend fun getLocations(): Flow<List<GetDeliveryLocation>> {
        return deliveryLocationDao.getAll().map { it.map { it.toDomain() } }
    }

    override suspend fun addLocation(location: GetDeliveryLocation) {
        deliveryLocationDao.addDeliveryLocation(location.toData())
    }

}