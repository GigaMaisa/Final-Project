package com.example.final_project.data.repository.remote.firebase

import com.example.final_project.data.remote.common.HandleErrorStates
import com.example.final_project.data.remote.common.Resource
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.repository.forgot_pass.ForgotPasswordRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class FirebaseForgotPasswordRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
): ForgotPasswordRepository {
    override suspend fun sendVerificationEmail(email: String): Flow<Resource<Boolean>> {
        return callbackFlow<Resource<Boolean>> {
            trySend(Resource.Loading(true))

            auth.sendPasswordResetEmail(email)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        trySend(Resource.Success(true))
                    } else {
                        trySend(Resource.Error(error = HandleErrorStates.handleException(task.exception!!), throwable = task.exception!!))
                    }
                }

            trySend(Resource.Loading(false))
            awaitClose {}
        }.flowOn(ioDispatcher)
    }
}