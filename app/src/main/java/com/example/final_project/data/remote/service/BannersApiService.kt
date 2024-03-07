package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.BannerDto
import retrofit2.Response
import retrofit2.http.GET

interface BannersApiService {
//    @GET("ee89b810-16c3-4b75-9bcb-6e4d597438a3?fbclid=IwAR3tDc5sPoEHI9pbhjLPfxAr7uHOyH66hFM5NyqLNWChr9K0jNJSo4RWx6g")
//    suspend fun getBanners(): Response<List<BannerDto>>

    @GET("banners.json")
    suspend fun getBanners(): Response<List<BannerDto>>
}