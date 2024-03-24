package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(private val orderDetailsRepository: OrderDetailsRepository) {
    suspend operator fun invoke(getOrder: GetOrder) {
        orderDetailsRepository.addOrder(getOrder)
    }
}