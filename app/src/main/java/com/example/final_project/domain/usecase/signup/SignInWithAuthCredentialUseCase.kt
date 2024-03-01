package com.example.final_project.domain.usecase.signup

import com.example.final_project.data.remote.common.Resource
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SignInWithAuthCredentialUseCase @Inject constructor(private val firebaseAuthRepository: FirebaseAuthRepository) {
    suspend operator fun invoke(credential: PhoneAuthCredential) : Flow<Resource<AuthResult>> {
        return firebaseAuthRepository.signInWithPhoneAuthCredential(credential = credential)
    }
}