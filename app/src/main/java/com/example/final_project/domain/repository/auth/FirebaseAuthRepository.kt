package com.example.final_project.domain.repository.auth

import android.app.Activity
import com.example.final_project.data.remote.common.Resource
import kotlinx.coroutines.flow.Flow

interface FirebaseAuthRepository {
    suspend fun firebaseSendVerificationCodeToPhoneNumber(phoneNumber: String, activity: Activity) : Flow<Resource<String>>
}