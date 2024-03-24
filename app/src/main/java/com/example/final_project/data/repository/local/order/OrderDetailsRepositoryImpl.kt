package com.example.final_project.data.repository.local.order

import com.example.final_project.data.local.dao.OrderDao
import com.example.final_project.data.local.mapper.order.toData
import com.example.final_project.data.local.mapper.order.toDomain
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject

class OrderDetailsRepositoryImpl @Inject constructor(
    private val orderDao: OrderDao,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : OrderDetailsRepository {
    override fun getAllOrders(): Flow<List<GetOrder>> {
        return orderDao.getAllOrders().map { it.map { it.toDomain() } }.flowOn(ioDispatcher)
    }

    override suspend fun addOrder(order: GetOrder)= withContext(ioDispatcher) {
        orderDao.addOrder(order.toData())
    }

    override fun getCartItemsNumber(): Flow<Int> {
        return orderDao.getCartItemsNumber()
    }

    override suspend fun deleteOrders() = withContext(ioDispatcher) {
        orderDao.deleteAllRows()
    }

    override suspend fun deleteSpecificOrder(foodId: String) = withContext(ioDispatcher) {
        orderDao.deleteOrderDetailById(foodId)
    }

    override suspend fun getLastAddedRestaurantId(): Int? = withContext(ioDispatcher) {
        return@withContext orderDao.getLastAddedRestaurantId()
    }

    override suspend fun updateItemQuantity(order: GetOrder) = withContext(ioDispatcher) {
        orderDao.updateOrderQuantity(order.toData())
    }
}