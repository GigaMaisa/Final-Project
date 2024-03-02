package com.example.final_project.domain.usecase.home

import com.example.final_project.domain.repository.home.BannerRepository
import javax.inject.Inject

class GetBannersUseCase @Inject constructor(private val bannerRepository: BannerRepository) {
    suspend operator fun invoke() = bannerRepository.getBanners()
}