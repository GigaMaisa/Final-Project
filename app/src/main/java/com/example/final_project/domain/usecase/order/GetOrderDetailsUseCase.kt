package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetOrderDetailsUseCase @Inject constructor(private val orderDetailsRepository: OrderDetailsRepository) {
    operator fun invoke():  Flow<List<GetOrder>> {
        return orderDetailsRepository.getAllOrders()
    }
}