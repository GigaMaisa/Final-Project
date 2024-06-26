package com.example.final_project.data.repository.remote.distance

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.mapper.distance.toDomain
import com.example.final_project.data.remote.service.GoogleDistanceMatrixApiService
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.model.distance.GetDistance
import com.example.final_project.domain.repository.distance.DistanceRepository
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.withContext
import retrofit2.await
import java.lang.Exception
import javax.inject.Inject

class DistanceRepositoryImpl @Inject constructor(
    private val googleDistanceMatrixApiService: GoogleDistanceMatrixApiService,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): DistanceRepository {
    override suspend fun getDistanceAndDuration(
        origin: LatLng,
        destination: LatLng
    ): Flow<Resource<GetDistance>> = withContext(ioDispatcher) {
        return@withContext flow<Resource<GetDistance>> {
            emit(Resource.Loading(true))

            val response = googleDistanceMatrixApiService.getDistanceMatrix("${origin.latitude},${origin.longitude}", "${destination.latitude},${destination.longitude}").await()
            emit(Resource.Success(response.toDomain()))

        }.catch { e ->
            emit(Resource.Error(error = HandleErrorStates.handleException(e as Exception), throwable = e))
        }.onCompletion {
            emit(Resource.Loading(false))
        }

    }
}