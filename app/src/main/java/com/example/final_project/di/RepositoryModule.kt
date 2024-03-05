package com.example.final_project.di

import com.example.final_project.data.remote.common.EmailSignInResponseHandler
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.service.BannersApiService
import com.example.final_project.data.repository.remote.firebase.FirebaseAdditionalUserDataRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebasePhonePhoneAuthRepositoryImpl
import com.example.final_project.data.repository.remote.chat.ChatContactsRepositoryImpl
import com.example.final_project.data.repository.remote.chat.ChatMessagesRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseAuthStateRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseEmailLoginRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebasePhotosRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseSignOutRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseUserDataRepositoryImpl
import com.example.final_project.data.repository.remote.home.BannerRepositoryImpl
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.repository.auth.FirebaseAdditionalUserDataRepository
import com.example.final_project.domain.repository.auth.FirebaseAuthStateRepository
import com.example.final_project.domain.repository.auth.FirebaseEmailLoginRepository
import com.example.final_project.domain.repository.auth.FirebasePhoneAuthRepository
import com.example.final_project.domain.repository.auth.FirebaseSignOutRepository
import com.example.final_project.domain.repository.chat.ChatContactsRepository
import com.example.final_project.domain.repository.chat.ChatMessagesRepository
import com.example.final_project.domain.repository.firebase.FirebasePhotosRepository
import com.example.final_project.domain.repository.firebase.FirebaseUserDataRepository
import com.example.final_project.domain.repository.home.BannerRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.storage.FirebaseStorage
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
    fun provideFirebaseAuthRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebasePhoneAuthRepository {
        return FirebasePhonePhoneAuthRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideFirebaseAdditionalUserDataRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseAdditionalUserDataRepository {
        return FirebaseAdditionalUserDataRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideFirebaseSignOutRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseSignOutRepository {
        return FirebaseSignOutRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideEmailSignInRepository(auth: FirebaseAuth, emailSignInResponseHandler: EmailSignInResponseHandler) : FirebaseEmailLoginRepository {
        return FirebaseEmailLoginRepositoryImpl(auth = auth, emailSignInResponseHandler = emailSignInResponseHandler)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthStateRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseAuthStateRepository {
        return FirebaseAuthStateRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideFirebaseUserDataRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebaseUserDataRepository {
        return FirebaseUserDataRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideFirebasePhotosRepository(auth: FirebaseAuth, firebaseStorage: FirebaseStorage) : FirebasePhotosRepository {
        return FirebasePhotosRepositoryImpl(auth = auth, firebaseStorage = firebaseStorage)
    }

    @Provides
    @Singleton
    fun provideChatRepository(databaseReference: DatabaseReference): ChatMessagesRepository = ChatMessagesRepositoryImpl(databaseReference)

    @Provides
    @Singleton
    fun provideChatContactsRepository(databaseReference: DatabaseReference): ChatContactsRepository = ChatContactsRepositoryImpl(databaseReference)

    @Provides
    @Singleton
    fun provideBannerRepository(bannersApiService: BannersApiService, responseHandler: ResponseHandler): BannerRepository = BannerRepositoryImpl(bannersApiService, responseHandler)
}