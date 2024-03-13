package com.example.final_project.domain.repository.favourites

import com.example.final_project.domain.model.restaurant.GetRestaurant
import kotlinx.coroutines.flow.Flow

interface FavouriteRestaurantsRepository {
    fun getAllFavouriteRestaurants(): Flow<List<GetRestaurant>>
    suspend fun getSingleFavouriteRestaurant(restaurantId: Int): GetRestaurant?
    suspend fun addFavouriteRestaurant(restaurant: GetRestaurant)
    suspend fun deleteFavouriteRestaurant(restaurant: GetRestaurant)
}