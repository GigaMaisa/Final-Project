package com.example.final_project.data.local.database

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.final_project.data.local.dao.CardDao
import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.dao.FavouriteRestaurantDao
import com.example.final_project.data.local.model.CardEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity
import com.example.final_project.data.local.model.RestaurantEntity

@Database(
    entities = [DeliveryLocationEntity::class, CardEntity::class, RestaurantEntity::class],
    version = 3,
    exportSchema = true,
    autoMigrations = [AutoMigration(from = 1, to = 2), AutoMigration(from = 2, to = 3)]
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun deliveryLocationDao(): DeliveryLocationDao
    abstract fun cardDao(): CardDao
    abstract fun FavouriteRestaurantDao(): FavouriteRestaurantDao
}