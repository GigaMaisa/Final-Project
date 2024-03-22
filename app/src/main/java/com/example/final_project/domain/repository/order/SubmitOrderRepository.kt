package com.example.final_project.domain.repository.order

import com.example.final_project.domain.model.order.GetSubmitOrder
import com.google.firebase.auth.FirebaseUser

interface SubmitOrderRepository {
    val currentUser: FirebaseUser?
    suspend fun addOrder(submitOrder: GetSubmitOrder)
}