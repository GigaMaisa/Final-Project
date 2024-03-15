package com.example.final_project.domain.repository.distance

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.distance.GetDistance
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow

interface DistanceRepository {
    suspend fun getDistanceAndDuration(origin: LatLng, destination: LatLng): Flow<Resource<GetDistance>>
}