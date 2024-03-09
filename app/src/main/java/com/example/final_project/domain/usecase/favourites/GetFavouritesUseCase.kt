package com.example.final_project.domain.usecase.favourites

import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import javax.inject.Inject

class GetFavouritesUseCase @Inject constructor(private val favouriteRestaurantsRepository: FavouriteRestaurantsRepository) {
    operator fun invoke() = favouriteRestaurantsRepository.getAllFavouriteRestaurants()
}