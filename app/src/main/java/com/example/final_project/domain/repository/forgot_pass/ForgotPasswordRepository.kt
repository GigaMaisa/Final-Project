package com.example.final_project.domain.repository.forgot_pass

import com.example.final_project.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

interface ForgotPasswordRepository {
    suspend fun sendVerificationEmail(email: String) : Flow<Resource<Boolean>>
}