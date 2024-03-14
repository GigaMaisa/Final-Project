package com.example.final_project.domain.repository.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.model.restaurant.GetRestaurant
import kotlinx.coroutines.flow.Flow

interface OrderDetailsRepository {
    fun getAllOrders(): Flow<List<GetOrder>>
    suspend fun addOrder(order: GetOrder)
    suspend fun deleteOrders()
}