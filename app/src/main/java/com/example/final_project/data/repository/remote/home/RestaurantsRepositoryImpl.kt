package com.example.final_project.data.repository.remote.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.home.toDomain
import com.example.final_project.data.remote.service.RestaurantsApiService
import com.example.final_project.domain.model.Restaurant.GetRestaurant
import com.example.final_project.domain.repository.home.RestaurantsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestaurantsRepositoryImpl @Inject constructor(
    private val restaurantsApiService: RestaurantsApiService,
    private val responseHandler: ResponseHandler
) : RestaurantsRepository {
    override suspend fun getRestaurants(): Flow<Resource<List<GetRestaurant>>> {
        return responseHandler.safeApiCall {
            restaurantsApiService.getRestaurants()
        }.asResource {
            it.map { it.toDomain() }
        }
    }

    override suspend fun getRestaurantById(id: Int): Flow<Resource<GetRestaurant>> {
        return responseHandler.safeApiCall {
            restaurantsApiService.getRestaurantsById(id = id)
        }.asResource {
            it.toDomain()
        }
    }

}
