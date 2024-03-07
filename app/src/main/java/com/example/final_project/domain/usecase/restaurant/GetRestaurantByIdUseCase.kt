package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.domain.repository.home.RestaurantsRepository
import javax.inject.Inject

class GetRestaurantByIdUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(id: Int) = restaurantsRepository.getRestaurantById(id = id)
}