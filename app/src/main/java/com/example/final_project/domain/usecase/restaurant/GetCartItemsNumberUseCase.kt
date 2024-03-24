package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.domain.repository.order.OrderDetailsRepository
import javax.inject.Inject

class GetCartItemsNumberUseCase @Inject constructor(private val orderDetailsRepository: OrderDetailsRepository){
    operator fun invoke() = orderDetailsRepository.getCartItemsNumber()
}