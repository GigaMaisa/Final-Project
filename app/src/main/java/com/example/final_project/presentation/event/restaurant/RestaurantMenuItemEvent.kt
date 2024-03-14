package com.example.final_project.presentation.event.restaurant

import com.example.final_project.presentation.model.restaurant.MenuItemAdditions
import com.example.final_project.presentation.model.restaurant.MenuItemDetails

sealed class RestaurantMenuItemEvent {
    class SetRestaurantDetailsEvent(val menuItemDetails: MenuItemDetails): RestaurantMenuItemEvent()
    class UpdateErrorMessage(val errorMessage: Int): RestaurantMenuItemEvent()
    class UpdateRestaurantDetailsEvent(val menuItemDetails: MenuItemAdditions): RestaurantMenuItemEvent()
}
