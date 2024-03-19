package com.example.final_project.di

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import com.example.final_project.data.local.dao.CardDao
import com.example.final_project.data.local.dao.DeliveryLocationDao
import com.example.final_project.data.local.dao.FavouriteRestaurantDao
import com.example.final_project.data.local.dao.OrderDao
import com.example.final_project.data.local.datasource.ChatBotAuthTokenDataSource
import com.example.final_project.data.remote.common.EmailSignInResponseHandler
import com.example.final_project.data.remote.common.ResponseHandler
import com.example.final_project.data.remote.service.BannersApiService
import com.example.final_project.data.remote.service.ChatBotApiService
import com.example.final_project.data.remote.service.DirectionsApiService
import com.example.final_project.data.remote.service.GoogleDistanceMatrixApiService
import com.example.final_project.data.remote.service.RestaurantDetailsApiService
import com.example.final_project.data.remote.service.RestaurantsApiService
import com.example.final_project.data.repository.local.card.CardRepositoryImpl
import com.example.final_project.data.repository.local.datastore.DataStoreRepositoryImpl
import com.example.final_project.data.repository.local.delivery_location.DeliveryLocationRepositoryImpl
import com.example.final_project.data.repository.local.favourites.FavouriteRestaurantsRepositoryImpl
import com.example.final_project.data.repository.local.order.OrderDetailsRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseAdditionalUserDataRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebasePhonePhoneAuthRepositoryImpl
import com.example.final_project.data.repository.remote.chat.ChatContactsRepositoryImpl
import com.example.final_project.data.repository.remote.chat.ChatMessagesRepositoryImpl
import com.example.final_project.data.repository.remote.chatbot.ChatBotRepositoryImpl
import com.example.final_project.data.repository.remote.distance.DistanceRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseAuthStateRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseEmailLoginRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebasePhotosRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseSignOutRepositoryImpl
import com.example.final_project.data.repository.remote.firebase.FirebaseUserDataRepositoryImpl
import com.example.final_project.data.repository.remote.home.BannerRepositoryImpl
import com.example.final_project.data.repository.remote.home.RestaurantsRepositoryImpl
import com.example.final_project.data.repository.remote.order.SubmitOrderRepositoryImpl
import com.example.final_project.data.repository.remote.restaurant_details.RestaurantDetailsRepositoryImpl
import com.example.final_project.data.repository.remote.route.DirectionsRepositoryImpl
import com.example.final_project.di.DispatchersModule.IoDispatcher
import com.example.final_project.domain.repository.auth.FirebaseAdditionalUserDataRepository
import com.example.final_project.domain.repository.auth.FirebaseAuthStateRepository
import com.example.final_project.domain.repository.auth.FirebaseEmailLoginRepository
import com.example.final_project.domain.repository.auth.FirebasePhoneAuthRepository
import com.example.final_project.domain.repository.auth.FirebaseSignOutRepository
import com.example.final_project.domain.repository.bot.ChatBotRepository
import com.example.final_project.domain.repository.card.CardRepository
import com.example.final_project.domain.repository.chat.ChatContactsRepository
import com.example.final_project.domain.repository.chat.ChatMessagesRepository
import com.example.final_project.domain.repository.datastore.DataStoreRepository
import com.example.final_project.domain.repository.delivery_location.DeliveryLocationRepository
import com.example.final_project.domain.repository.distance.DistanceRepository
import com.example.final_project.domain.repository.favourites.FavouriteRestaurantsRepository
import com.example.final_project.domain.repository.firebase.FirebasePhotosRepository
import com.example.final_project.domain.repository.firebase.FirebaseUserDataRepository
import com.example.final_project.domain.repository.home.BannerRepository
import com.example.final_project.domain.repository.home.RestaurantsRepository
import com.example.final_project.domain.repository.order.OrderDetailsRepository
import com.example.final_project.domain.repository.order.SubmitOrderRepository
import com.example.final_project.domain.repository.restaurant_details.RestaurantDetailsRepository
import com.example.final_project.domain.repository.route.DirectionsRepository
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
    fun provideRestaurantsDetailsRepository(restaurantDetailsApiService: RestaurantDetailsApiService, responseHandler: ResponseHandler): RestaurantDetailsRepository =
        RestaurantDetailsRepositoryImpl(restaurantDetailsApiService, responseHandler)

    @Provides
    @Singleton
    fun provideFavouriteRestaurantsRepository(dao: FavouriteRestaurantDao, @IoDispatcher ioDispatcher: CoroutineDispatcher): FavouriteRestaurantsRepository =
        FavouriteRestaurantsRepositoryImpl(dao, ioDispatcher)

    @Provides
    @Singleton
    fun provideSubmitOrderRepository(databaseReference: DatabaseReference, @IoDispatcher ioDispatcher: CoroutineDispatcher): SubmitOrderRepository =
        SubmitOrderRepositoryImpl(databaseReference, ioDispatcher)

    @Provides
    @Singleton
    fun provideDirectionsRepository(directionsApiService: DirectionsApiService, @IoDispatcher ioDispatcher: CoroutineDispatcher): DirectionsRepository =
        DirectionsRepositoryImpl(directionsApiService, ioDispatcher )

    @Provides
    @Singleton
    fun provideDistanceRepository(distanceMatrixApiService: GoogleDistanceMatrixApiService, @IoDispatcher ioDispatcher: CoroutineDispatcher): DistanceRepository =
        DistanceRepositoryImpl(distanceMatrixApiService, ioDispatcher)

    @Provides
    @Singleton
    fun provideDeliveryLocationRepository(dao: DeliveryLocationDao, @IoDispatcher ioDispatcher: CoroutineDispatcher): DeliveryLocationRepository =
        DeliveryLocationRepositoryImpl(deliveryLocationDao = dao, ioDispatcher = ioDispatcher)
    @Provides
    @Singleton
    fun provideDataStoreRepository(dataStore: DataStore<Preferences>) : DataStoreRepository {
        return DataStoreRepositoryImpl(dataStore = dataStore)
    }

    @Provides
    @Singleton
    fun provideFirebaseAuthRepository(auth: FirebaseAuth, @IoDispatcher ioDispatcher: CoroutineDispatcher) : FirebasePhoneAuthRepository {
        return FirebasePhonePhoneAuthRepositoryImpl(auth = auth, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideOrderRepository(orderDao: OrderDao, @IoDispatcher ioDispatcher: CoroutineDispatcher) : OrderDetailsRepository {
        return OrderDetailsRepositoryImpl(orderDao = orderDao, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideChatBotRepository(responseHandler: ResponseHandler, chatBotApiService: ChatBotApiService, chatBotAuthTokenDataSource: ChatBotAuthTokenDataSource) : ChatBotRepository {
        return ChatBotRepositoryImpl(handler = responseHandler, chatBotApiService = chatBotApiService, chatBotAuthTokenDataSource = chatBotAuthTokenDataSource)
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
    fun provideCardRepository(dao: CardDao, @IoDispatcher ioDispatcher: CoroutineDispatcher): CardRepository {
        return CardRepositoryImpl(cardDao = dao, ioDispatcher = ioDispatcher)
    }

    @Provides
    @Singleton
    fun provideChatRepository(auth: FirebaseAuth, databaseReference: DatabaseReference): ChatMessagesRepository = ChatMessagesRepositoryImpl(auth = auth, databaseReference = databaseReference)

    @Provides
    @Singleton
    fun provideChatContactsRepository(auth: FirebaseAuth ,databaseReference: DatabaseReference): ChatContactsRepository = ChatContactsRepositoryImpl(auth = auth, databaseReference = databaseReference)

    @Provides
    @Singleton
    fun provideRestaurantsRepository(restaurantsApiService: RestaurantsApiService, responseHandler: ResponseHandler): RestaurantsRepository = RestaurantsRepositoryImpl(restaurantsApiService, responseHandler)

    @Provides
    @Singleton
    fun provideBannerRepository(bannersApiService: BannersApiService, responseHandler: ResponseHandler): BannerRepository = BannerRepositoryImpl(bannersApiService, responseHandler)
}