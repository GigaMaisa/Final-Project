package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.BannerDto
import retrofit2.Response
import retrofit2.http.GET

interface BannersApiService {
    @GET("b185074b-3d1f-4e1c-9361-10c6f58539d5?fbclid=IwAR10vjewUohlCkqBG4EEbHNETvg-SdDOHYYS7FDcwt0s9RLxZnI9U2rYGPA")
    suspend fun getBanners(): Response<List<BannerDto>>
}