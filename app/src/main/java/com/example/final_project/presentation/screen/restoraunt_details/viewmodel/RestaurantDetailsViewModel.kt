package com.example.final_project.presentation.screen.restoraunt_details.viewmodel

import android.util.Log.d
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.favourites.AddFavouriteUseCase
import com.example.final_project.domain.usecase.favourites.DeleteFavouriteUseCase
import com.example.final_project.domain.usecase.favourites.GetSingleFavouriteUseCase
import com.example.final_project.domain.usecase.restaurant.GetRestaurantDetailsUseCase
import com.example.final_project.presentation.event.restaurant.RestaurantDetailsEvent
import com.example.final_project.presentation.mapper.restaurant.toDomain
import com.example.final_project.presentation.mapper.restaurant.toPresentation
import com.example.final_project.presentation.model.restaurant.Restaurant
import com.example.final_project.presentation.state.RestaurantDetailsState
import com.example.final_project.presentation.util.getErrorMessage
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
    private val getSingleFavouriteUseCase: GetSingleFavouriteUseCase,
    private val getRestaurantDetailsUseCase: GetRestaurantDetailsUseCase
) : ViewModel() {

    private val _restaurantDetailsStateFlow = MutableStateFlow(RestaurantDetailsState())
    val restaurantMenuStateFlow = _restaurantDetailsStateFlow.asStateFlow()

    fun onEvent(event: RestaurantDetailsEvent) {
        when(event) {
            is RestaurantDetailsEvent.AddFavouriteEvent -> addFavourite(restaurant = event.restaurant)
            is RestaurantDetailsEvent.GetIfFavouriteEvent -> getIfFavourite(restaurantId = event.restaurantId)
            is RestaurantDetailsEvent.RemoverFavouriteEvent -> deleteFavourite(restaurant = event.restaurant)
            is RestaurantDetailsEvent.GetRestaurantDetailsEvent -> getRestaurantDetails(restaurantId = event.restaurantId)
            is RestaurantDetailsEvent.UpdateErrorMessageEvent -> updateErrorMessage(errorMessage = event.errorMessage)
        }
    }

    private fun getIfFavourite(restaurantId: Int) {
        viewModelScope.launch {
            _restaurantDetailsStateFlow.update { currentState ->
                currentState.copy(
                    isFavourite = getSingleFavouriteUseCase(
                        restaurantId = restaurantId
                    ) == null
                )
            }
        }
    }

    private fun getRestaurantDetails(restaurantId: Int) {
        viewModelScope.launch {
            getRestaurantDetailsUseCase(restaurantId).collect {resource ->
                d("resourceRestaurantDetails", resource.toString())
                when(resource) {
                    is Resource.Loading -> _restaurantDetailsStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _restaurantDetailsStateFlow.update { currentState -> currentState.copy(restaurantDetails = resource.response.toPresentation()) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
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

    private fun updateErrorMessage(errorMessage: Int?) {
        _restaurantDetailsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }
}