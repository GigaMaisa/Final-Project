package com.example.final_project.data.remote.service

import com.example.final_project.data.remote.model.BannerDto
import retrofit2.Response
import retrofit2.http.GET

interface BannersApiService {
    @GET("b185074b-3d1f-4e1c-9361-10c6f58539d5?fbclid=IwAR2B3D2rMTDxO95gbIfyhIWLf5nCIBmCxO0oPjaSmZAZ_rx43RIPd-49ABI")
    suspend fun getBanners(): Response<List<BannerDto>>
}