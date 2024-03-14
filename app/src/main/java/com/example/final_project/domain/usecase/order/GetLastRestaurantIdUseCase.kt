package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.repository.order.OrderDetailsRepository
import javax.inject.Inject

class GetLastRestaurantIdUseCase @Inject constructor(private val orderDetailsRepository: OrderDetailsRepository) {
    suspend operator fun invoke(): Int? {
        return orderDetailsRepository.getLastAddedRestaurantId()
    }
}