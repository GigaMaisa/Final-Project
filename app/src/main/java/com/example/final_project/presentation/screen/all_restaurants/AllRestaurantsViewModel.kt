package com.example.final_project.presentation.screen.all_restaurants

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.favourites.GetFavouritesUseCase
import com.example.final_project.domain.usecase.restaurant.GetRestaurantByCategoryUseCase
import com.example.final_project.domain.usecase.restaurant.GetRestaurantsUseCase
import com.example.final_project.presentation.event.restaurant.AllRestaurantsEvent
import com.example.final_project.presentation.extension.toCategory
import com.example.final_project.presentation.mapper.restaurant.toPresentation
import com.example.final_project.presentation.model.restaurant.RestaurantType
import com.example.final_project.presentation.state.AllRestaurantsState
import com.example.final_project.presentation.util.getErrorMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllRestaurantsViewModel @Inject constructor(
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getRestaurantByCategoryUseCase: GetRestaurantByCategoryUseCase
) : ViewModel() {
    private val _restaurantsStateFlow = MutableStateFlow(AllRestaurantsState())
    val restaurantsStateFlow = _restaurantsStateFlow.asStateFlow()

    private val _uiState = MutableSharedFlow<AllRestaurantsUiEvents>()
    val uiState = _uiState.asSharedFlow()

    fun onEvent(event: AllRestaurantsEvent) {
        when (event) {
            is AllRestaurantsEvent.GoBackEvent -> viewModelScope.launch {
                _uiState.emit(
                    AllRestaurantsUiEvents.NavigateBack
                )
            }

            is AllRestaurantsEvent.GetAllRestaurantsEvent -> getRestaurants()
            is AllRestaurantsEvent.GoToRestaurantsDetailsEvent -> viewModelScope.launch {
                _uiState.emit(
                    AllRestaurantsUiEvents.NavigateToRestaurantDetails(restaurantId = event.restaurantId)
                )
            }

            is AllRestaurantsEvent.UpdateErrorMessageEvent -> updateErrorMessage(errorMessage = event.message)
            is AllRestaurantsEvent.GetFavouriteRestaurants -> getFavouriteRestaurants()
            is AllRestaurantsEvent.GetRestaurantByCategoryEvent -> getFilteredRestaurants(restaurantType = event.restaurantType)
        }
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            getRestaurantsUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _restaurantsStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _restaurantsStateFlow.update { currentState -> currentState.copy(restaurants = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun getFavouriteRestaurants() {
        viewModelScope.launch {
            getFavouritesUseCase().collect {
                _restaurantsStateFlow.update { currentState -> currentState.copy(restaurants = it.map { it.toPresentation() }) }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _restaurantsStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    private fun getFilteredRestaurants(restaurantType: RestaurantType) {
        viewModelScope.launch {
            getRestaurantByCategoryUseCase(restaurantType.toCategory()).collect {resource ->
                when(resource) {
                    is Resource.Loading -> _restaurantsStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _restaurantsStateFlow.update { currentState -> currentState.copy(restaurants = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    sealed interface AllRestaurantsUiEvents {
        object NavigateBack : AllRestaurantsUiEvents
        class NavigateToRestaurantDetails(val restaurantId: Int) : AllRestaurantsUiEvents
    }
}