package com.example.final_project.presentation.screen.restoraunt_details.bottomsheet

import androidx.lifecycle.ViewModel
import com.example.final_project.presentation.event.restaurant.RestaurantMenuItemEvent
import com.example.final_project.presentation.model.restaurant.MenuItemAdditions
import com.example.final_project.presentation.model.restaurant.MenuItemDetails
import com.example.final_project.presentation.state.RestaurantMenuItemState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RestaurantMenuItemViewModel: ViewModel() {
    private val _restaurantMenuItemStateFlow = MutableStateFlow(RestaurantMenuItemState())
    val restaurantMenuItemStateFlow = _restaurantMenuItemStateFlow.asStateFlow()

    fun onEvent(event: RestaurantMenuItemEvent) {
        when(event) {
            is RestaurantMenuItemEvent.SetRestaurantDetailsEvent -> setRestaurantDetails(menuItemDetails = event.menuItemDetails)
            is RestaurantMenuItemEvent.UpdateErrorMessage -> updateErrorMessage(errorMessage = event.errorMessage)
            is RestaurantMenuItemEvent.UpdateRestaurantDetailsEvent -> updateSelectedOption(menuItemAdditions = event.menuItemDetails)
        }
    }

    private fun setRestaurantDetails(menuItemDetails: MenuItemDetails) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(menuItem = menuItemDetails) }
    }

    private fun updateSelectedOption(menuItemAdditions: MenuItemAdditions) {
        val updatedMenuAdditions = _restaurantMenuItemStateFlow.value.menuItem?.menuItemAdditions?.map {
            if (menuItemAdditions == it) {
                menuItemAdditions
            }else
                it
        }
        updatedMenuAdditions?.let {updatedMenu->
            _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(menuItem = _restaurantMenuItemStateFlow.value.menuItem?.copy(menuItemAdditions = updatedMenu)) }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _restaurantMenuItemStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }
}