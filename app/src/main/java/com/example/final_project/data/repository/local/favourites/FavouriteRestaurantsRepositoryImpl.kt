package com.example.final_project.data.repository.local.favourites

import com.example.final_project.data.local.dao.FavouriteRestaurantDao
import com.example.final_project.data.local.mapper.restaurant.toData
import com.example.final_project.data.local.mapper.restaurant.toDomain
import com.example.final_project.di.DispatchersModule
import com.example.final_project.domain.model.Restaurant.GetRestaurant
import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FavouriteRestaurantsRepositoryImpl @Inject constructor(
    private val favouriteRestaurantDao: FavouriteRestaurantDao,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
): FavouriteRestaurantsRepository {
    override fun getAllFavouriteRestaurants(): Flow<List<GetRestaurant>> {
        return favouriteRestaurantDao.getAllFavouriteRestaurants().map { it.map { it.toDomain() } }.flowOn(ioDispatcher)
    }

    override suspend fun getSingleFavouriteRestaurant(restaurantId: Int): GetRestaurant? {
        return favouriteRestaurantDao.getSingleFavouriteRestaurant(restaurantId = restaurantId)?.toDomain()
    }

    override suspend fun addFavouriteRestaurant(restaurant: GetRestaurant) = withContext(ioDispatcher) {
        favouriteRestaurantDao.addFavouriteRestaurant(restaurantEntity = restaurant.toData())
    }

    override suspend fun deleteFavouriteRestaurant(restaurant: GetRestaurant) {
        favouriteRestaurantDao.deleteFavouriteRestaurant(restaurantEntity = restaurant.toData())
    }

}