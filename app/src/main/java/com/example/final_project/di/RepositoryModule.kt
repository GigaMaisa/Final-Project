package com.example.final_project.di

import com.example.final_project.data.repository.remote.ChatRepositoryImpl
import com.example.final_project.data.repository.remote.FirebaseAuthRepositoryImpl
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.repository.ChatRepository
import com.example.final_project.domain.repository.auth.FirebaseAuthRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
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
    fun provideFirebaseAuthRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseAuthRepository {
        return FirebaseAuthRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideChatRepository(databaseReference: DatabaseReference): ChatRepository = ChatRepositoryImpl(databaseReference)
}