package com.example.final_project.domain.usecase.signup

import android.app.Activity
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.PhoneAuthOptions
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SendVerificationCodeUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend operator fun invoke(phoneNumber: String, options: PhoneAuthOptions.Builder) : Flow<Resource<String>> {
        return firebaseAuthRepository.firebaseSendVerificationCodeToPhoneNumber(
            phoneNumber = phoneNumber,
            optionsBuilder = options
        )
    } 
}