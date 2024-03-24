package com.example.final_project.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.final_project.data.local.model.OrderDetailsEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface OrderDao {
    @Query("SELECT * FROM order_details")
    fun getAllOrders(): Flow<List<OrderDetailsEntity>>

    @Query("SELECT restaurantId FROM order_details LIMIT 1")
    suspend fun getLastAddedRestaurantId(): Int?

    @Query("SELECT count(*) FROM order_details")
    fun getCartItemsNumber(): Flow<Int>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addOrder(orderDetailsEntity: OrderDetailsEntity)

    @Query("DELETE FROM order_details")
    suspend fun deleteAllRows()

    @Query("DELETE FROM order_details WHERE foodId = :foodId")
    suspend fun deleteOrderDetailById(foodId: String)

    @Update
    suspend fun updateOrderQuantity(orderDetails: OrderDetailsEntity)
}