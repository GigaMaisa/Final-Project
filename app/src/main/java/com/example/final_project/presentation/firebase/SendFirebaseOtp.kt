package com.example.final_project.presentation.firebase

import android.app.Activity
import android.util.Log
import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.presentation.state.VerificationState
import com.example.final_project.presentation.util.getErrorMessage
import com.google.firebase.FirebaseException
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class SendFirebaseOtp @Inject constructor() {

    @Inject
    lateinit var auth: FirebaseAuth

    @IoDispatcher
    @Inject
    lateinit var ioDispatcher: CoroutineDispatcher

     suspend fun firebaseSendVerificationCodeToPhoneNumber(phoneNumber: String, activity: Activity): Flow<VerificationState> {
        return callbackFlow {
            trySend(VerificationState(isLoading = true))

            val callbacks = object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                    Log.d("RACXA", "${credential.smsCode}")
                }

                override fun onVerificationFailed(exception: FirebaseException) {
                    trySend(VerificationState(errorMessage = getErrorMessage(HandleErrorStates.handleException(exception))))
                    Log.d("RACXA FAILED", "${exception.message}")
                }

                override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                    trySend(VerificationState(data = verificationId))
                    Log.d("RACXA SENT", verificationId)
                }
            }

            val options = PhoneAuthOptions.newBuilder(FirebaseAuth.getInstance())
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setCallbacks(callbacks)
                .setActivity(activity)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)

            awaitClose {

            }
        }.flowOn(Dispatchers.IO)
    }
}