package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.domain.repository.home.RestaurantsRepository
import javax.inject.Inject

class GetRestaurantByCategoryUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(type: String) = restaurantsRepository.getRestaurants().asResource {
        it.filter { it.type == type }
    }
}