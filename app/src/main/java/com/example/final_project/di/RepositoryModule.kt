package com.example.final_project.di

import com.example.final_project.data.repository.remote.FirebaseAuthRepositoryImpl
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(auth: FirebaseAuth, @DispatchersModule.IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }
}