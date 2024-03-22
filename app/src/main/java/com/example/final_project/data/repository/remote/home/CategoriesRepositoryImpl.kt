package com.example.final_project.data.repository.remote.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.home.toDomain
import com.example.final_project.data.remote.service.CategoriesApiService
import com.example.final_project.domain.model.home.GetCategoryType
import com.example.final_project.domain.repository.home.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val categoriesApiService: CategoriesApiService,
    private val responseHandler: ResponseHandler,
): CategoriesRepository {
    override suspend fun getCategories(): Flow<Resource<List<GetCategoryType>>> {
        return responseHandler.safeApiCall {
            categoriesApiService.getCategories()
        }.asResource {
            it.values.map { it.toDomain() }
        }
    }
}