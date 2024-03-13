package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.restaurant.RestaurantDetailsDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RestaurantDetailsApiService {
    @GET("menu/{id}.json")
    suspend fun getRestaurantsDetailsById(@Path("id") id: Int): Response<RestaurantDetailsDto>
}