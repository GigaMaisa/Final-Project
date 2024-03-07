package com.example.final_project.data.repository.remote.route

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.mapper.route.toDomain
import com.example.final_project.data.remote.service.DirectionsApiService
import com.example.final_project.domain.model.GetDirection
import com.example.final_project.domain.repository.route.DirectionsRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import retrofit2.await
import java.lang.Exception
import javax.inject.Inject

class DirectionsRepositoryImpl @Inject constructor(
    private val directionsApiService: DirectionsApiService,
): DirectionsRepository {
    override suspend fun getDirections(
        origin: LatLng,
        destination: LatLng
    ): Flow<Resource<GetDirection>> = withContext(IO) {
        return@withContext flow<Resource<GetDirection>> {
            emit(Resource.Loading(true))

            val response = directionsApiService.getDirections("${origin.latitude},${origin.longitude}", "${destination.latitude},${destination.longitude}").await()
            emit(Resource.Success(response.toDomain()))
        }.catch { e ->
            emit(Resource.Error(
                    error = HandleErrorStates.handleException(e as Exception),
                    throwable = e
                )
            )
        }.onCompletion {
            emit(Resource.Loading(false))
        }

    }


}