package com.example.final_project.data.repository.remote.restaurant_details

import android.util.Log.d
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.restaurant.toDomain
import com.example.final_project.data.remote.service.RestaurantDetailsApiService
import com.example.final_project.domain.model.restaurant.GetRestaurantDetails
import com.example.final_project.domain.repository.restaurant_details.RestaurantDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class RestaurantDetailsRepositoryImpl @Inject constructor(
    private val restaurantDetailsApiService: RestaurantDetailsApiService,
    private val responseHandler: ResponseHandler
) : RestaurantDetailsRepository {
    override suspend fun getRestaurantDetails(restaurantId: Int): Flow<Resource<GetRestaurantDetails>> {
        return responseHandler.safeApiCall {
            restaurantDetailsApiService.getRestaurantsDetailsById(restaurantId)
        }.asResource {
            it.toDomain()
        }
    }
}