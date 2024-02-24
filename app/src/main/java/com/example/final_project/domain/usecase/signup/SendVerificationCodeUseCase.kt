package com.example.final_project.domain.usecase.signup

import android.app.Activity
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend operator fun invoke(phoneNumber: String, activity: Activity) : Flow<Resource<String>> {
        return firebaseAuthRepository.firebaseSendVerificationCodeToPhoneNumber(phoneNumber = phoneNumber, activity = activity)
    } 
}