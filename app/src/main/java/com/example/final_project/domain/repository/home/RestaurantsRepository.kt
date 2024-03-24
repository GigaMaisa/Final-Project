package com.example.final_project.domain.repository.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.restaurant.GetRestaurant
import kotlinx.coroutines.flow.Flow

interface RestaurantsRepository {
    suspend fun getRestaurants(): Flow<Resource<List<GetRestaurant>>>
    suspend fun getRestaurantById(id: Int): Flow<Resource<GetRestaurant>>
}