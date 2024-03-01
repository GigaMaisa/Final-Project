package com.example.final_project.domain.usecase.signup

import com.example.final_project.domain.repository.auth.FirebaseAdditionalUserDataRepository
import javax.inject.Inject

class GetUserUuidUseCase @Inject constructor(private val userAdditionalUserDataRepository: FirebaseAdditionalUserDataRepository) {
    operator fun invoke() : String {
        return userAdditionalUserDataRepository.getUserUuid()
    }
}