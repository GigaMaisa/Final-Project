package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.final_project.data.local.model.OrderDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM order_details")
    fun getAllOrders(): Flow<List<OrderDetailsEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orderDetailsEntity: OrderDetailsEntity)

    @Query("DELETE FROM order_details")
    suspend fun deleteAllRows()
}