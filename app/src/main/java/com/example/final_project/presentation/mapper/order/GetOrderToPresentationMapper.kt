package com.example.final_project.presentation.mapper.order

import com.example.final_project.domain.model.order.GetOrder
import com.example.final_project.presentation.model.order.Order

fun GetOrder.toPresentation() : Order {
    return Order(
        foodId = foodId,
        restaurantId = restaurantId,
        name = name,
        image = image,
        menuCategory = menuCategory,
        quantity = quantity,
        price = price
    )
}