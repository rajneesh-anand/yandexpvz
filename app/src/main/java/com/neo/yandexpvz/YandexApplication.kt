package com.neo.yandexpvz

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.work.Configuration
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.utils.TokenManager
import com.neo.yandexpvz.worker.DatabaseSyncWorker
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

//
//@HiltAndroidApp
//class YandexApplication: Application()


@HiltAndroidApp
class YandexApplication() : Application(), Configuration.Provider {
    @Inject
    lateinit var workerFactory: CustomWorkerFactory
    override fun onCreate() {
        super.onCreate()

        // Reading API key from BuildConfig.
        // Do not forget to add your MAPKIT_API_KEY property to local.properties file.
        MapKitFactory.setApiKey(BuildConfig.MAPKIT_API_KEY)
    }

    override fun getWorkManagerConfiguration() =
        Configuration.Builder()
            .setMinimumLoggingLevel(android.util.Log.INFO)
            .setWorkerFactory(workerFactory)
            .build()
}

class CustomWorkerFactory @Inject constructor(private val coinRepository: CoinRepository, private val tokenManager: TokenManager) : WorkerFactory(){
    override fun createWorker(
        appContext: Context,
        workerClassName: String,
        workerParameters: WorkerParameters
    ): ListenableWorker = DatabaseSyncWorker(coinRepository,tokenManager, appContext,workerParameters )

}
