package com.example.final_project.data.repository.local.delivery_location

import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.mapper.deliveryLocation.toData
import com.example.final_project.data.local.mapper.deliveryLocation.toDomain
import com.example.final_project.domain.model.GetDeliveryLocation
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DeliveryLocationRepositoryImpl @Inject constructor(private val deliveryLocationDao: DeliveryLocationDao, private val ioDispatcher: CoroutineDispatcher): DeliveryLocationRepository {
    override fun getLocations(): Flow<List<GetDeliveryLocation>> {
        return deliveryLocationDao.getAll().map { it.map { it.toDomain() } }.flowOn(ioDispatcher)
    }

    override suspend fun addLocation(location: GetDeliveryLocation) = withContext(ioDispatcher) {
        deliveryLocationDao.addDeliveryLocation(location.toData())
    }

}