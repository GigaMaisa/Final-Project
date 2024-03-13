package com.example.final_project.domain.repository.restaurant_details

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.restaurant.GetRestaurantDetails
import kotlinx.coroutines.flow.Flow

interface RestaurantDetailsRepository {
    suspend fun getRestaurantDetails(restaurantId: Int): Flow<Resource<GetRestaurantDetails>>
}