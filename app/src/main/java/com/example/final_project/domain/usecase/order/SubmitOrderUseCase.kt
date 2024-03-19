package com.example.final_project.domain.usecase.order

import com.example.final_project.domain.model.order.GetSubmitOrder
import com.example.final_project.domain.repository.order.SubmitOrderRepository
import javax.inject.Inject

class SubmitOrderUseCase @Inject constructor(private val submitOrderRepository: SubmitOrderRepository) {
    suspend operator fun invoke(getSubmitOrder: GetSubmitOrder) = submitOrderRepository.addOrder(getSubmitOrder)
}