package com.example.final_project.domain.repository.order

import com.example.final_project.domain.model.order.GetSubmitOrder

interface SubmitOrderRepository {
    suspend fun addOrder(submitOrder: GetSubmitOrder)
}