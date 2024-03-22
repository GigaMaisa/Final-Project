package com.example.final_project.domain.usecase.home

import com.example.final_project.domain.repository.home.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(private val categoriesRepository: CategoriesRepository) {
    suspend operator fun invoke() = categoriesRepository.getCategories()
}