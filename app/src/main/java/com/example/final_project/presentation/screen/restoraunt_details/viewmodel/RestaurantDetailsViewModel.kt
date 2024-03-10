package com.example.final_project.presentation.screen.restoraunt_details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.domain.usecase.favourites.AddFavouriteUseCase
import com.example.final_project.domain.usecase.favourites.DeleteFavouriteUseCase
import com.example.final_project.domain.usecase.favourites.GetSingleFavouriteUseCase
import com.example.final_project.presentation.event.RestaurantDetailsEvent
import com.example.final_project.presentation.mapper.favourites.toDomain
import com.example.final_project.presentation.model.Restaurant
import com.example.final_project.presentation.model.RestaurantMenu
import com.example.final_project.presentation.state.RestaurantMenuState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RestaurantDetailsViewModel @Inject constructor(
    private val addFavouriteUseCase: AddFavouriteUseCase,
    private val deleteFavouriteUseCase: DeleteFavouriteUseCase,
    private val getSingleFavouriteUseCase: GetSingleFavouriteUseCase
) : ViewModel() {
    val menu = listOf(
        RestaurantMenu(1, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(2, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(3, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(4, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(5, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(6, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(7, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(8, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(9, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(10, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(11, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        RestaurantMenu(12, "https://imageproxy.wolt.com/wolt-frontpage-images/content_editor/banners/images/7b14eb2c-cbdd-11ee-8fed-7a8d78a26100_2211bd94_7971_48a6_bd5d_bccef580e91d.jpg?w=600", "PIZZA MARGARITA", "pizza", "3.5", "40", "4.3"),
        )
    private val _restaurantMenuStateFlow = MutableStateFlow(RestaurantMenuState(menu = menu))
    val restaurantMenuStateFlow = _restaurantMenuStateFlow.asStateFlow()

    fun onEvent(event: RestaurantDetailsEvent) {
        when(event) {
            is RestaurantDetailsEvent.AddFavouriteEvent -> addFavourite(restaurant = event.restaurant)
            is RestaurantDetailsEvent.GetIfFavouriteEvent -> getIfFavourite(restaurantId = event.restaurantId)
            is RestaurantDetailsEvent.RemoverFavouriteEvent -> deleteFavourite(restaurant = event.restaurant)
        }
    }

    private fun getIfFavourite(restaurantId: Int) {
        viewModelScope.launch {
            _restaurantMenuStateFlow.update { currentState ->
                currentState.copy(
                    isFavourite = getSingleFavouriteUseCase(
                        restaurantId = restaurantId
                    ) == null
                )
            }
        }
    }

    private fun addFavourite(restaurant: Restaurant) {
        viewModelScope.launch {
            addFavouriteUseCase(restaurant.toDomain())
        }
    }

    private fun deleteFavourite(restaurant: Restaurant) {
        viewModelScope.launch {
            deleteFavouriteUseCase(restaurant.toDomain())
        }
    }
}