package com.example.final_project.domain.repository.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.home.GetCategoryType
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<Resource<List<GetCategoryType>>>
}