package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.BannerDto
import retrofit2.Response
import retrofit2.http.GET

interface BannersApiService {
    @GET("banners.json")
    suspend fun getBanners(): Response<List<BannerDto>>
}