package com.example.final_project.domain.usecase.forgot_pass

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.data.repository.remote.firebase.FirebaseForgotPasswordRepositoryImpl
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ResetPasswordViaEmailUseCase @Inject constructor(private val repository: FirebaseForgotPasswordRepositoryImpl) {
    suspend operator fun invoke(email: String) : Flow<Resource<Boolean>> {
        return repository.sendVerificationEmail(email)
    }
}