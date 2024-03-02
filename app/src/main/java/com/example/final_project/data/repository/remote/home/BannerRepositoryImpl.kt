package com.example.final_project.data.repository.remote.home

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.mapper.base.asResource
import com.example.final_project.data.remote.mapper.home.toDomain
import com.example.final_project.data.remote.service.BannersApiService
import com.example.final_project.domain.model.home.GetBanner
import com.example.final_project.domain.repository.home.BannerRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    private val bannersApiService: BannersApiService,
    private val responseHandler: ResponseHandler
): BannerRepository {
    override suspend fun getBanners(): Flow<Resource<List<GetBanner>>> {
        return responseHandler.safeApiCall {
            bannersApiService.getBanners()
        }.asResource {
            it.map { it.toDomain() }
        }
    }

}