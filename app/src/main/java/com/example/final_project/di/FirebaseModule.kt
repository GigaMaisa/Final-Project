package com.example.final_project.di

import android.content.Context
import com.example.final_project.presentation.activity.MainActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object FirebaseModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth() = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore() = FirebaseFirestore.getInstance()

    @Provides
    fun providePhoneAuthOptions(auth: FirebaseAuth, @ActivityContext activity: Context): PhoneAuthOptions.Builder {
        return PhoneAuthOptions.newBuilder(auth)
            .setTimeout(60L, TimeUnit.SECONDS)
            .setActivity(activity as MainActivity)
    }

}