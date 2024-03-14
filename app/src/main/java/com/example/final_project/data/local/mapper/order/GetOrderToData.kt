package com.example.final_project.data.local.mapper.order

import com.example.final_project.data.local.model.OrderDetailsEntity
import com.example.final_project.domain.model.order.GetOrder

fun GetOrder.toData() : OrderDetailsEntity {
    return OrderDetailsEntity(
        foodId = foodId,
        restaurantId = restaurantId,
        name = name,
        image = image,
        menuCategory = menuCategory,
        quantity = quantity,
        price = price
    )
}