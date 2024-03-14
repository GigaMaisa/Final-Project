package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.model.order.Order

sealed class CartEvent {
    object GetAllOrders : CartEvent()
    class AddCartItemQuantityEvent(val cartItem: Order): CartEvent()
    data class AddOrder(val order: Order) : CartEvent()
    class RemoveCartItemQuantityEvent(val cartItem: Order): CartEvent()
    object CalculateCheckoutEvent : CartEvent()
    object DeleteAllOrders : CartEvent()
    class DeleteItemEvent(val id: String): CartEvent()
}
