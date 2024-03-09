package com.example.final_project.domain.usecase.favourites

import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import javax.inject.Inject

class GetSingleFavouriteUseCase @Inject constructor(private val favouriteRestaurantsRepository: FavouriteRestaurantsRepository) {
    suspend operator fun invoke(restaurantId: Int) = favouriteRestaurantsRepository.getSingleFavouriteRestaurant(restaurantId = restaurantId)
}