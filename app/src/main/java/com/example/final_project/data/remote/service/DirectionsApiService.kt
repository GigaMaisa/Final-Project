package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.DirectionsResponseDto
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface DirectionsApiService {

    @GET("/maps/api/directions/json")
    fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("key") apiKey: String = "AIzaSyCPGjltMDP3NnzPMyqBLgC_w5KNhh3Lj3I"
    ): Call<DirectionsResponseDto>
}