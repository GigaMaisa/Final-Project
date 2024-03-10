package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_project.data.local.model.RestaurantEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavouriteRestaurantDao {

    @Query("SELECT * FROM favourite_restaurants")
    fun getAllFavouriteRestaurants(): Flow<List<RestaurantEntity>>

    @Query("SELECT * FROM favourite_restaurants where restaurantId = :restaurantId")
    suspend fun getSingleFavouriteRestaurant(restaurantId: Int): RestaurantEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addFavouriteRestaurant(restaurantEntity: RestaurantEntity)

    @Delete
    suspend fun deleteFavouriteRestaurant(restaurantEntity: RestaurantEntity)
}