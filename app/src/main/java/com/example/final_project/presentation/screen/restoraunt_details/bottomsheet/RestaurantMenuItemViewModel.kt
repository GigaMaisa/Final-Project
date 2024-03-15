package com.example.final_project.presentation.screen.restoraunt_details.bottomsheet

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.order.AddOrderUseCase
import com.example.final_project.domain.usecase.order.DeleteAllOrdersUseCase
import com.example.final_project.domain.usecase.order.GetLastRestaurantIdUseCase
import com.example.final_project.presentation.event.restaurant.RestaurantMenuItemEvent
import com.example.final_project.presentation.mapper.order.toDomain
import com.example.final_project.presentation.model.order.Order
import com.example.final_project.presentation.model.restaurant.MenuItemAdditions
import com.example.final_project.presentation.model.restaurant.MenuOrderDetails
import com.example.final_project.presentation.state.RestaurantMenuItemState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantMenuItemViewModel @Inject constructor(
    private val getLastRestaurantIdUseCase: GetLastRestaurantIdUseCase,
    private val deleteAllOrdersUseCase: DeleteAllOrdersUseCase,
    private val addOrderUseCase: AddOrderUseCase
) : ViewModel() {
    private val _restaurantMenuItemStateFlow = MutableStateFlow(RestaurantMenuItemState())
    val restaurantMenuItemStateFlow = _restaurantMenuItemStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<RestaurantMenuItemUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: RestaurantMenuItemEvent) {
        when (event) {
            is RestaurantMenuItemEvent.SetRestaurantDetailsEvent -> setRestaurantDetails(
                menuOrderDetails = event.menuOrderDetails
            )

            is RestaurantMenuItemEvent.UpdateErrorMessage -> updateErrorMessage(errorMessage = event.errorMessage)
            is RestaurantMenuItemEvent.UpdateRestaurantDetailsEvent -> updateSelectedOption(
                menuItemAdditions = event.menuItemDetails
            )

            is RestaurantMenuItemEvent.MinusQuantityEvent -> minusQuantity(quantity = event.quantity)
            is RestaurantMenuItemEvent.PlusQuantityEvent -> plusQuantity(quantity = event.quantity)
            is RestaurantMenuItemEvent.UpdateTotalPriceEvent -> updateTotalPrice()
            is RestaurantMenuItemEvent.AddItemToCartEvent -> addItemToCart()
            is RestaurantMenuItemEvent.AddRestaurantIdEvent -> addRestaurantId(restaurantId = event.restaurantId)
        }
    }

    private fun setRestaurantDetails(menuOrderDetails: MenuOrderDetails) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(menuItem = menuOrderDetails.menuItemDetails, restaurantId = menuOrderDetails.restaurantId, category = menuOrderDetails.menuCategory) }
    }

    private fun updateSelectedOption(menuItemAdditions: MenuItemAdditions) {
        val updatedMenuAdditions =
            _restaurantMenuItemStateFlow.value.menuItem?.menuItemAdditions?.map {
                if (menuItemAdditions == it) {
                    menuItemAdditions
                } else
                    it
            }
        updatedMenuAdditions?.let { updatedMenu ->
            _restaurantMenuItemStateFlow.update { currentState ->
                currentState.copy(
                    menuItem = _restaurantMenuItemStateFlow.value.menuItem?.copy(
                        menuItemAdditions = updatedMenu
                    )
                )
            }
        }
    }

    private fun plusQuantity(quantity: Int) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(quantity = quantity + 1) }
        updateTotalPrice()
    }

    private fun minusQuantity(quantity: Int) {
        if (quantity != 1) {
            _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(quantity = quantity - 1) }
            updateTotalPrice()
        }
    }

    private fun updateTotalPrice() {
        _restaurantMenuItemStateFlow.value.menuItem?.let {
            val totalPrice = it.price * _restaurantMenuItemStateFlow.value.quantity
            _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(totalPrice = totalPrice) }
        }

    }

    private fun addItemToCart() {
        viewModelScope.launch {
            d("showResutlBro", "${getLastRestaurantIdUseCase()} - ${_restaurantMenuItemStateFlow.value.restaurantId}")
            if (getLastRestaurantIdUseCase() == null || getLastRestaurantIdUseCase() == _restaurantMenuItemStateFlow.value.restaurantId) {
                addCartItem()
            } else {
                deleteAllOrdersUseCase()
                addCartItem()
            }
        }
    }

    private fun addCartItem() {
        viewModelScope.launch {
            with(_restaurantMenuItemStateFlow.value) {
                menuItem?.let {menu->
                    val order = Order(foodId = menu.id, restaurantId = restaurantId!!, name = menu.name, image = menu.image, menuCategory = category!!, quantity = quantity, price = totalPrice!!)
                    addOrderUseCase(order.toDomain())
                    _uiEvent.emit(RestaurantMenuItemUiEvent.DismissBottomSheetEvent)
                }
            }
        }
    }


    private fun addRestaurantId(restaurantId: Int) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(restaurantId = restaurantId) }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    sealed interface RestaurantMenuItemUiEvent {
        object DismissBottomSheetEvent: RestaurantMenuItemUiEvent
    }
}