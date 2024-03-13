package com.example.final_project.domain.usecase.restaurant

import com.example.final_project.domain.repository.restaurant_details.RestaurantDetailsRepository
import javax.inject.Inject

class GetRestaurantDetailsUseCase @Inject constructor(private val restaurantDetailsRepository: RestaurantDetailsRepository) {
    suspend operator fun invoke(restaurantId: Int) = restaurantDetailsRepository.getRestaurantDetails(restaurantId = restaurantId)
}