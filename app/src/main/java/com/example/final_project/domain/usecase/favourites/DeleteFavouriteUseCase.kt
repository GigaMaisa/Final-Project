package com.example.final_project.domain.usecase.favourites

import com.example.final_project.domain.model.Restaurant.GetRestaurant
import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import javax.inject.Inject

class DeleteFavouriteUseCase @Inject constructor(private val favouriteRestaurantsRepository: FavouriteRestaurantsRepository) {
    suspend operator fun invoke(restaurant: GetRestaurant) = favouriteRestaurantsRepository.deleteFavouriteRestaurant(restaurant)
}