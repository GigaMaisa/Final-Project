package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.CategoryTypeDto
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesApiService {
    @GET("type.json")
    suspend fun getCategories(): Response<Map<String, CategoryTypeDto>>
}