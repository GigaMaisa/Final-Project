package com.example.final_project.presentation.screen.cart

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.model.CartItem
import com.example.final_project.presentation.model.Checkout
import com.example.final_project.presentation.state.CartState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class CartViewModel : ViewModel() {
    val cartItems = listOf(
        CartItem(1, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 50f, 2, 3.5f),
        CartItem(2, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 1, 3.5f),
        CartItem(3, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 4, 3.5f),
        CartItem(4, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 5, 3.5f),
        CartItem(5, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 1, 3.5f),
        CartItem(6, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 6, 3.5f),
        CartItem(7, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 1, 3.5f),
        CartItem(8, "https://upload.wikimedia.org/wikipedia/commons/thumb/5/50/Khinkali_551.jpg/1200px-Khinkali_551.jpg", "Khinkali", "Qartuli", 20.7f, 2, 3.5f),
        )
    private val _cartStateFlow = MutableStateFlow(CartState())
    val cartStateFlow = _cartStateFlow.asStateFlow()

    init {
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = cartItems) }
        calculateCheckout()
    }

    private fun calculateCheckout() {
        var subPrice = 0f
        var deliveryFee = 0f
        _cartStateFlow.value.cartItems?.forEach {
            subPrice += (it.price*it.quantity)
            deliveryFee += it.deliveryFee
        }
        _cartStateFlow.update { currentState -> currentState.copy(checkout = Checkout(0, subPrice, deliveryFee)) }
    }

    fun onSwipeDelete(id: Int) {
        val oldCheckout = _cartStateFlow.value.checkout
        val deletedCartItem = _cartStateFlow.value.cartItems?.find { it.id == id }
        val newCheckout = _cartStateFlow.value.checkout?.copy(subTotal = oldCheckout!!.subTotal - (deletedCartItem?.price!! * deletedCartItem.quantity), chargeTotal = (deletedCartItem.deliveryFee) )
        _cartStateFlow.update { currentState -> currentState.copy(cartItems = currentState.cartItems?.filter { it.id != id }, checkout = newCheckout) }
    }
}