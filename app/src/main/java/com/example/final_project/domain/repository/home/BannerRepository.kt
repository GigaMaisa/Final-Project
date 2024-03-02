package com.example.final_project.domain.repository.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.model.home.GetBanner
import kotlinx.coroutines.flow.Flow

interface BannerRepository {
    suspend fun getBanners(): Flow<Resource<List<GetBanner>>>
}