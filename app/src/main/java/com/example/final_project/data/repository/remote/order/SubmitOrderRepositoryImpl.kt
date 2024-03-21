package com.example.final_project.data.repository.remote.order

import com.example.final_project.data.remote.mapper.order.toData
import com.example.final_project.di.DispatchersModule
import com.example.final_project.domain.model.order.GetSubmitOrder
import com.example.final_project.domain.repository.order.SubmitOrderRepository
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SubmitOrderRepositoryImpl @Inject constructor(
    private val databaseReference: DatabaseReference,
    @DispatchersModule.IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : SubmitOrderRepository {
    override suspend fun addOrder(submitOrder: GetSubmitOrder): Unit = withContext(ioDispatcher) {
        val orderRef = databaseReference.child("orders").child("0")
        orderRef.setValue(submitOrder.toData()).await()
    }
}