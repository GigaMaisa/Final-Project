package com.example.final_project.domain.repository.auth

import android.app.Activity
import com.example.final_project.data.remote.common.Resource
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import dagger.Provides
import kotlinx.coroutines.flow.Flow


interface FirebaseAuthRepository {
    suspend fun firebaseSendVerificationCodeToPhoneNumber(phoneNumber: String, optionsBuilder : PhoneAuthOptions.Builder) : Flow<Resource<String>>

    suspend fun signInWithPhoneAuthCredential(credential: PhoneAuthCredential) : Flow<Resource<AuthResult>>
}