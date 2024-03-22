package com.example.final_project.data.repository.local.delivery_location

import android.util.Log
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

    override fun getLocation(): Flow<GetDeliveryLocation> {
        return deliveryLocationDao.getOneLocation().map { it.toDomain() }.flowOn(ioDispatcher)
    }

    override suspend fun updateLocation(location: GetDeliveryLocation) = withContext(ioDispatcher) {
        deliveryLocationDao.updateDefaultLocation(location.toData())
    }

    override suspend fun deleteLocation(location: GetDeliveryLocation) = withContext(ioDispatcher) {
        Log.d("clickIsNotHappening31", location.toData().toString())
        deliveryLocationDao.deleteLocation(location.toData())
    }

    override suspend fun updateDefaultToFalse() = withContext(ioDispatcher) {
        deliveryLocationDao.updateDefaultToFalse()
    }

    override suspend fun addLocation(location: GetDeliveryLocation) = withContext(ioDispatcher) {
        deliveryLocationDao.addDeliveryLocation(location.toData())
    }

}