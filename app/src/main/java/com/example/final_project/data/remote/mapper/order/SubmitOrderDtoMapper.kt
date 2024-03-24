package com.example.final_project.data.remote.mapper.order

import com.example.final_project.data.remote.model.order.SubmitOrderDto
import com.example.final_project.domain.model.order.GetSubmitOrder

fun GetSubmitOrder.toData() = SubmitOrderDto(
    active = isActive,
    location = location.toDto(),
    totalPrice = totalPrice,
    menu = menu.map { it.toDto() }
)