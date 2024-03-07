package com.example.final_project.di

import com.example.final_project.BuildConfig
import com.example.final_project.data.remote.common.EmailSignInResponseHandler
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.service.BannersApiService
import com.example.final_project.data.remote.service.DirectionsApiService
import com.example.final_project.data.remote.service.RestaurantsApiService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class MockyRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class GoogleMapRetrofit

    @Qualifier
    @Retention(AnnotationRetention.BINARY)
    annotation class FirebaseRetrofit

    @Provides
    @Singleton
    fun provideEmailSignInResponseHandler(@DispatchersModule.IoDispatcher ioDispatcher: CoroutineDispatcher): EmailSignInResponseHandler {
        return EmailSignInResponseHandler(ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideMoshi(): Moshi {
        return Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    }

    @Provides
    @Singleton
    fun provideHandleResponse(): ResponseHandler = ResponseHandler()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val client = OkHttpClient.Builder()
        if (BuildConfig.DEBUG) {
            client.addInterceptor(httpLoggingInterceptor)
        }
        return client.build()
    }

    @Provides
    @Singleton
    @MockyRetrofit
    fun provideMockyRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.MOCKY_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @GoogleMapRetrofit
    fun provideGoogleMapRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.GOOGLE_BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @FirebaseRetrofit
    fun provideFirebaseRetrofitClient(moshi: Moshi, okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.FIREBASE_API_SERVICE)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .client(okHttpClient)
            .build()
    }

//    @Provides
//    @Singleton
//    fun provideBannersApiService(@MockyRetrofit retrofit: Retrofit): BannersApiService {
//        return retrofit.create(BannersApiService::class.java)
//    }

    @Provides
    @Singleton
    fun provideRestaurantsApiService(@FirebaseRetrofit retrofit: Retrofit): RestaurantsApiService {
        return retrofit.create(RestaurantsApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideBannersApiService(@FirebaseRetrofit retrofit: Retrofit): BannersApiService {
        return retrofit.create(BannersApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideDirectionsApiService(@GoogleMapRetrofit retrofit: Retrofit): DirectionsApiService {
        return retrofit.create(DirectionsApiService::class.java)
    }
}