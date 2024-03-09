package com.example.final_project.presentation.event

import com.example.final_project.presentation.model.cart.CartItem

sealed class CartEvent {
    class AddCartItemQuantityEvent(val cartItem: CartItem): CartEvent()
    class RemoveCartItemQuantityEvent(val cartItem: CartItem): CartEvent()
    object CalculateCheckoutEvent : CartEvent()
    class DeleteItemEvent(val id: Int): CartEvent()
}
