package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import javax.inject.Inject

class UpdateItemQuantityUseCase @Inject constructor(private val orderDetailsRepository: OrderDetailsRepository) {
    suspend operator fun invoke(order: GetOrder) {
        orderDetailsRepository.updateItemQuantity(order = order)
    }
}