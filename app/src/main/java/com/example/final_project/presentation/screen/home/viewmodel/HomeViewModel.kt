package com.example.final_project.presentation.screen.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.usecase.favourites.GetFavouritesUseCase
import com.example.final_project.domain.usecase.home.GetBannersUseCase
import com.example.final_project.domain.usecase.home.GetCategoriesUseCase
import com.example.final_project.domain.usecase.restaurant.GetRestaurantsUseCase
import com.example.final_project.presentation.event.home.HomeEvent
import com.example.final_project.presentation.mapper.restaurant.toPresentation
import com.example.final_project.presentation.mapper.home.toPresentation
import com.example.final_project.presentation.model.restaurant.RestaurantType
import com.example.final_project.presentation.state.HomeState
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
class HomeViewModel @Inject constructor(
    private val getBannersUseCase: GetBannersUseCase,
    private val getRestaurantsUseCase: GetRestaurantsUseCase,
    private val getFavouritesUseCase: GetFavouritesUseCase,
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {

    private val _homeStateFlow = MutableStateFlow(HomeState())
    val homeStateFlow = _homeStateFlow.asStateFlow()

    private val _uiEvent = MutableSharedFlow<HomeUiEvent>()
    val uiEvent = _uiEvent.asSharedFlow()

    fun onEvent(event: HomeEvent) {
        when(event) {
            is HomeEvent.GetBannersEvent -> getBanners()
            is HomeEvent.GetRestaurantsEvent -> getRestaurants()
            is HomeEvent.GetFavouriteRestaurantsEvent -> getFavouriteRestaurants()
            is HomeEvent.UpdateErrorMessageEvent -> updateErrorMessage(errorMessage = event.errorMessage)
            is HomeEvent.GetCategoriesEvent -> getCategories()
            is HomeEvent.GoToAllRestaurantsEvent -> viewModelScope.launch {
                _uiEvent.emit(HomeUiEvent.GoToAllRestaurantsFragment(event.restaurantType, event.searchFilter)
                )
            }

            is HomeEvent.GoToRestaurantDetailsEvent -> viewModelScope.launch { _uiEvent.emit(HomeUiEvent.GoToRestaurantDetailsFragment(event.restaurantId)) }
        }
    }

    private fun getBanners() {
        viewModelScope.launch {
            getBannersUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _homeStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _homeStateFlow.update { currentState -> currentState.copy(banners = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun getRestaurants() {
        viewModelScope.launch {
            getRestaurantsUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _homeStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _homeStateFlow.update { currentState -> currentState.copy(restaurants = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun getFavouriteRestaurants() {
        viewModelScope.launch {
            getFavouritesUseCase().collect {
                _homeStateFlow.update { currentState -> currentState.copy(favouriteRestaurants = it.map { it.toPresentation() }) }
            }
        }
    }

    private fun getCategories() {
        viewModelScope.launch {
            getCategoriesUseCase().collect {resource ->
                when(resource) {
                    is Resource.Loading -> _homeStateFlow.update { currentState -> currentState.copy(isLoading = resource.loading) }
                    is Resource.Success -> _homeStateFlow.update { currentState -> currentState.copy(categories = resource.response.map { it.toPresentation() }) }
                    is Resource.Error -> updateErrorMessage(getErrorMessage(resource.error))
                }
            }
        }
    }

    private fun updateErrorMessage(errorMessage: Int?) {
        _homeStateFlow.update { currentState -> currentState.copy(errorMessage = errorMessage) }
    }

    sealed interface HomeUiEvent {
        class GoToAllRestaurantsFragment(val restaurantType: RestaurantType = RestaurantType.ALL, val searchFilter: String? = null): HomeUiEvent
        class GoToRestaurantDetailsFragment(val restaurantId: Int): HomeUiEvent
    }
}