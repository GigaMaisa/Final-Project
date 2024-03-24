package com.example.final_project.domain.repository.order

import com.example.final_project.domain.model.order.GetOrder
import kotlinx.coroutines.flow.Flow

interface OrderDetailsRepository {
    fun getAllOrders(): Flow<List<GetOrder>>
    suspend fun addOrder(order: GetOrder)
    fun getCartItemsNumber(): Flow<Int>
    suspend fun deleteOrders()
    suspend fun deleteSpecificOrder(foodId: String)
    suspend fun getLastAddedRestaurantId(): Int?
    suspend fun updateItemQuantity(order: GetOrder)
}