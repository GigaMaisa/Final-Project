package com.example.final_project.domain.usecase.favourites

import com.example.final_project.domain.model.restaurant.GetRestaurant
import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import javax.inject.Inject

class AddFavouriteUseCase @Inject constructor(private val favouriteRestaurantsRepository: FavouriteRestaurantsRepository) {
    suspend operator fun invoke(restaurant: GetRestaurant) = favouriteRestaurantsRepository.addFavouriteRestaurant(restaurant)
}