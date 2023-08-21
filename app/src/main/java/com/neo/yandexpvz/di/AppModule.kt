package com.neo.yandexpvz.di

import android.content.Context
import androidx.room.Room
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.db.CoinDao
import com.neo.yandexpvz.db.CoinDatabase
import com.neo.yandexpvz.model.CoinEntity
import com.neo.yandexpvz.repository.DataStoreRepository
//import com.neo.yandexpvz.repository.FCMRepository
//import com.neo.yandexpvz.repository.FCMRepositoryImpl
import com.neo.yandexpvz.utils.Constants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideYandexApi(): RestApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(RestApi::class.java)
    }
    @Provides
    @Singleton
    fun provideCoinDatabase(@ApplicationContext context: Context): CoinDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            CoinDatabase::class.java,
            "coinDB.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDao(db: CoinDatabase): CoinDao = db.coinDao



    @Provides
    fun provideEntity() = CoinEntity()

//
//    @Provides
//    @Singleton
//
//    fun provideNetworkConnectivityService(application: Application): NetworkConnectivityService {
//        return NetworkConnectivityServiceImpl(application.connectivityManager)
//    }
//


    @Provides
    @Singleton
    fun provideDataStoreRepository(
        @ApplicationContext context: Context
    ) = DataStoreRepository(context = context)

}