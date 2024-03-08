package com.example.final_project.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.final_project.data.local.dao.CardDao
import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.model.CardEntity
import com.example.final_project.data.local.model.DeliveryLocationEntity

@Database(entities = [DeliveryLocationEntity::class, CardEntity::class], version = 1, exportSchema = true)
abstract class AppDataBase: RoomDatabase() {
    abstract fun deliveryLocationDao(): DeliveryLocationDao
    abstract fun cardDao(): CardDao
}