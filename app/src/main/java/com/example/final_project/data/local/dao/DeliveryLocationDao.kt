package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.final_project.data.local.model.DeliveryLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryLocationDao {
    @Query("SELECT * FROM delivery_location")
    fun getAll(): Flow<List<DeliveryLocationEntity>>

    @Insert
    fun addDeliveryLocation(deliveryLocation: DeliveryLocationEntity)

}