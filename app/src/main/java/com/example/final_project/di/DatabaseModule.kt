package com.example.final_project.di

import android.content.Context
import androidx.room.Room
import com.example.final_project.data.local.dao.CardDao
import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.database.AppDataBase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDataBase(@ApplicationContext context: Context): AppDataBase {
        return Room.databaseBuilder(context, AppDataBase::class.java, "app_database").build()
    }

    @Provides
    @Singleton
    fun provideDeliveryLocationDao(appDatabase: AppDataBase): DeliveryLocationDao {
        return appDatabase.deliveryLocationDao()
    }

    @Provides
    @Singleton
    fun provideCardDao(appDatabase: AppDataBase): CardDao {
        return appDatabase.cardDao()
    }
}