package com.example.final_project.data.repository.remote

import android.app.Activity
import android.util.Log
import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class FirebaseAuthRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : FirebaseAuthRepository{

    override suspend fun firebaseSendVerificationCodeToPhoneNumber(phoneNumber: String, activity: Activity): Flow<Resource<String>> {
        return callbackFlow {
            trySend(Resource.Loading(true))

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("RACXA", "${credential.smsCode}")
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    trySend(Resource.Error(error = HandleErrorStates.handleException(exception), throwable = exception))
                    Log.d("RACXA FAILED", "${exception.message}")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    trySend(Resource.Success(response = verificationId))
                    Log.d("RACXA SENT", verificationId)
                }
            }

            val options = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .setActivity(activity)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            awaitClose {

            }
        }.flowOn(ioDispatcher)
    }
}