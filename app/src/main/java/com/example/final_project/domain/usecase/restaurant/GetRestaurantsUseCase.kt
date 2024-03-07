package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.domain.repository.home.RestaurantsRepository
import javax.inject.Inject

class GetRestaurantsUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke() = restaurantsRepository.getRestaurants()
}