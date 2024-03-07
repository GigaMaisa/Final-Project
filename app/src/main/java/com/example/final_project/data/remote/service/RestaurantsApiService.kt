package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.RestaurantDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantsApiService {
    @GET("restaurants/{id}.json")
    suspend fun getRestaurantsById(@Path("id") id: Int): Response<RestaurantDto>

    @GET("restaurants.json")
    suspend fun getRestaurants(): Response<List<RestaurantDto>>
}