package com.example.final_project.presentation.mapper.order

import com.example.final_project.domain.model.order.GetSubmitOrder
import com.example.final_project.presentation.mapper.delivery_location.toDomain
import com.example.final_project.presentation.model.cart.SubmitOrder

fun SubmitOrder.toDomain() = GetSubmitOrder(
    isActive = isActive,
    location = location.toDomain(),
    menu = menu.map { it.toDomain() },
    totalPrice = totalPrice
)