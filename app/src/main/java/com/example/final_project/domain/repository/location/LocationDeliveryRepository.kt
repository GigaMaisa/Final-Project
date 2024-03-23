package com.example.final_project.domain.repository.location

import com.example.final_project.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

interface LocationDeliveryRepository {
    suspend fun getCourierLocation(): Flow<Resource<Map<String, Double>>>
}