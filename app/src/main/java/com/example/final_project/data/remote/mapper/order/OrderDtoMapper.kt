package com.example.final_project.data.remote.mapper.order

import com.example.final_project.data.remote.model.order.OrderDto
import com.example.final_project.domain.model.order.GetOrder

fun GetOrder.toDto() = OrderDto(
    name = name,
    category = menuCategory,
    quantity = quantity
)