package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import javax.inject.Inject

class AddOrderUseCase @Inject constructor(
    private val orderDetailsRepository: OrderDetailsRepository,
    private val getLastRestaurantIdUseCase: GetLastRestaurantIdUseCase
) {
    suspend operator fun invoke(getOrder: GetOrder) {
        if (getOrder.restaurantId == getLastRestaurantIdUseCase()) orderDetailsRepository.addOrder(getOrder)
    }
}