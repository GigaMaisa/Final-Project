package com.example.final_project.presentation.event.restaurant

import com.example.final_project.presentation.model.restaurant.MenuItemAdditions
import com.example.final_project.presentation.model.restaurant.MenuOrderDetails

sealed class RestaurantMenuItemEvent {
    class SetRestaurantDetailsEvent(val menuOrderDetails: MenuOrderDetails): RestaurantMenuItemEvent()
    class UpdateErrorMessage(val errorMessage: Int): RestaurantMenuItemEvent()
    class UpdateRestaurantDetailsEvent(val menuItemDetails: MenuItemAdditions): RestaurantMenuItemEvent()
    class MinusQuantityEvent(val quantity: Int): RestaurantMenuItemEvent()
    class PlusQuantityEvent(val quantity: Int): RestaurantMenuItemEvent()
    object UpdateTotalPriceEvent : RestaurantMenuItemEvent()
    object AddItemToCartEvent: RestaurantMenuItemEvent()
    class AddRestaurantIdEvent(val restaurantId: Int): RestaurantMenuItemEvent()
}
