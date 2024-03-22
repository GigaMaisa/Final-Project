package com.example.final_project.presentation.screen.cart.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.delivery_location.GetDeliveryLocationUseCase
import com.example.final_project.domain.usecase.order.AddOrderUseCase
import com.example.final_project.domain.usecase.order.DeleteAllOrdersUseCase
import com.example.final_project.domain.usecase.order.DeleteSpecificOrderUseCase
import com.example.final_project.domain.usecase.order.GetOrderDetailsUseCase
import com.example.final_project.domain.usecase.order.SubmitOrderUseCase
import com.example.final_project.domain.usecase.order.UpdateItemQuantityUseCase
import com.example.final_project.presentation.event.CartEvent
import com.example.final_project.presentation.mapper.delivery_location.toPresentation
import com.example.final_project.presentation.mapper.order.toDomain
import com.example.final_project.presentation.mapper.order.toPresentation
import com.example.final_project.presentation.model.cart.Checkout
import com.example.final_project.presentation.model.cart.SubmitOrder
import com.example.final_project.presentation.model.order.Order
import com.example.final_project.presentation.state.CartState
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getOrderDetailsUseCase: GetOrderDetailsUseCase,
    private val deleteAllOrdersUseCase: DeleteAllOrdersUseCase,
    private val addOrderUseCase: AddOrderUseCase,
    private val deleteSpecificOrderUseCase: DeleteSpecificOrderUseCase,
    private val updateItemQuantityUseCase: UpdateItemQuantityUseCase,
    private val getDeliveryLocationUseCase: GetDeliveryLocationUseCase,
    private val submitOrderUseCase: SubmitOrderUseCase,
): ViewModel() {
    private val _cartStateFlow = MutableStateFlow(CartState())
    val cartStateFlow = _cartStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<CartUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: CartEvent) {
        when(event) {
            is CartEvent.AddCartItemQuantityEvent -> increaseItemQuantity(cartItem = event.cartItem)
            is CartEvent.CalculateCheckoutEvent -> calculateCheckout()
            is CartEvent.DeleteItemEvent -> onSwipeDelete(id = event.id)
            is CartEvent.RemoveCartItemQuantityEvent -> decreaseItemQuantity(cartItem = event.cartItem)
            is CartEvent.GetAllOrders -> getAllOrdersFromDb()
            is CartEvent.AddOrder -> addOrder(event.order)
            is CartEvent.SubmitOrderEvent -> submitOrder()
            is CartEvent.GoBackEvent -> viewModelScope.launch { _uiEvent.emit(CartUiEvent.GoBackEvent) }
        }
    }

    init {
        updateDeliveryLocation()
    }

    private fun addOrder(order: Order) {
        viewModelScope.launch {
            addOrderUseCase(order.toDomain())
        }
    }

    private fun submitOrder() {
        viewModelScope.launch {
            val submitOrder = _cartStateFlow.value.cartItems?.let {orders->
                _cartStateFlow.value.deliveryLocation?.let { location ->
                    SubmitOrder(
                        isActive = true,
                        location = location,
                        menu = orders,
                        totalPrice = _cartStateFlow.value.checkout!!.chargeTotal + _cartStateFlow.value.checkout!!.subTotal,
                    )
                }
            }
            submitOrder?.let {
                submitOrderUseCase(it.toDomain())
                deleteAllOrdersUseCase()
                _uiEvent.emit(CartUiEvent.GoToDeliveryMapEvent(_cartStateFlow.value.deliveryLocation!!.location))
            }

        }
    }

    private fun updateDeliveryLocation() {
        viewModelScope.launch {
            getDeliveryLocationUseCase().collect {
                _cartStateFlow.update { currentState -> currentState.copy(deliveryLocation = it.toPresentation()) }
            }
        }
    }

    private fun getAllOrdersFromDb() {
        viewModelScope.launch {
            getOrderDetailsUseCase().collect {
                _cartStateFlow.update { currentState ->
                    currentState.copy(cartItems = it.map {
                        it.toPresentation()
                    })
                }

                calculateCheckout()
            }
        }
    }

    private fun calculateCheckout() {
        var subPrice = 0.0
        val deliveryFee = 3.0

        _cartStateFlow.value.cartItems?.forEach {
            subPrice += (it.price*it.quantity)
       }

        _cartStateFlow.update { currentState -> currentState.copy(checkout = Checkout(0, subPrice, deliveryFee)) }
    }

    private fun onSwipeDelete(id: String) {
        viewModelScope.launch {
            deleteSpecificOrderUseCase(foodId = id)
            calculateCheckout()
        }
    }

    private fun increaseItemQuantity(cartItem: Order) {
        viewModelScope.launch {
            _cartStateFlow.value.cartItems?.map {
                if (it == cartItem) {
                    updateItemQuantityUseCase(cartItem.copy(quantity = it.quantity + 1).toDomain())
                } else {
                    it
                }
            }

            calculateCheckout()
        }
    }

    private fun decreaseItemQuantity(cartItem: Order) {
        viewModelScope.launch {
            _cartStateFlow.value.cartItems?.map {
                if (it == cartItem) {
                    updateItemQuantityUseCase(cartItem.copy(quantity = it.quantity - 1).toDomain())
                } else {
                    it
                }
            }

            calculateCheckout()
        }
    }

    sealed interface CartUiEvent {
        object GoBackEvent : CartUiEvent
        class GoToDeliveryMapEvent(val latLng: LatLng): CartUiEvent
    }
}