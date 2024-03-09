package com.example.final_project.presentation.screen.cart.viewmodel

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.event.CartEvent
import com.example.final_project.presentation.model.cart.CartItem
import com.example.final_project.presentation.model.cart.Checkout
import com.example.final_project.presentation.state.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    val cartItems = listOf(
        CartItem(1, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 50.0, 2, 3.5),
        CartItem(2, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 4, 3.5),
        CartItem(4, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 5, 3.5),
        CartItem(5, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(6, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 6, 3.5),
        CartItem(7, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 1, 3.5),
        CartItem(8, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7, 2, 3.5),
        )
    private val _cartStateFlow = MutableStateFlow(CartState())
    val cartStateFlow = _cartStateFlow.asStateFlow()

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.AddCartItemQuantityEvent -> addItem(cartItem = event.cartItem)
            is CartEvent.CalculateCheckoutEvent -> calculateCheckout()
            is CartEvent.DeleteItemEvent -> onSwipeDelete(id = event.id)
            is CartEvent.RemoveCartItemQuantityEvent -> removeItem(cartItem = event.cartItem)
        }
    }

    init {
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = cartItems) }
        calculateCheckout()
    }

    private fun calculateCheckout() {
        var subPrice = 0.0
        var deliveryFee = 0.0

        _cartStateFlow.value.cartItems?.forEach {
            subPrice += (it.price*it.quantity)
            deliveryFee += it.deliveryFee
        }

        _cartStateFlow.update { currentState -> currentState.copy(checkout = Checkout(0, subPrice, deliveryFee)) }
    }

    private fun onSwipeDelete(id: Int) {
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = currentState.cartItems?.filter { it.id != id })}
        calculateCheckout()
    }

    private fun addItem(cartItem: CartItem) {
        val updatedCartItems = _cartStateFlow.value.cartItems?.map {
            if (it == cartItem) {
                it.copy(quantity = it.quantity + 1)
            } else {
                it
            }
        }
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = updatedCartItems) }
        calculateCheckout()
    }

    private fun removeItem(cartItem: CartItem) {
        val updatedCartItems = _cartStateFlow.value.cartItems?.map {
            if (it == cartItem) {
                it.copy(quantity = it.quantity - 1)
            } else {
                it
            }
        }
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = updatedCartItems) }
        calculateCheckout()
    }
}