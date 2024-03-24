package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.final_project.data.local.model.DeliveryLocationEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface DeliveryLocationDao {
    @Query("SELECT * FROM delivery_location")
    fun getAll(): Flow<List<DeliveryLocationEntity>>

    @Query("SELECT * FROM delivery_location WHERE isDeliveryDefault=1 LIMIT 1")
    fun getOneLocation(): Flow<DeliveryLocationEntity?>

    @Query("Update delivery_location set isDeliveryDefault=0")
    suspend fun updateDefaultToFalse()

    @Delete
    suspend fun deleteLocation(deliveryLocation: DeliveryLocationEntity)

    @Update
    suspend fun updateDefaultLocation(deliveryLocation: DeliveryLocationEntity)

    @Insert
    suspend fun addDeliveryLocation(deliveryLocation: DeliveryLocationEntity)

}