package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.domain.repository.home.RestaurantsRepository
import javax.inject.Inject

class GetFilteredRestaurantsUseCase @Inject constructor(private val restaurantsRepository: RestaurantsRepository) {
    suspend operator fun invoke(filter: String) = restaurantsRepository.getRestaurants().asResource {
        it.filter { it.title.lowercase().contains(filter) || it.type.lowercase().contains(filter) }
    }
}