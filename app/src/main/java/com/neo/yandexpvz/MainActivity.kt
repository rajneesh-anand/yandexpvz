package com.neo.yandexpvz

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.work.BackoffPolicy
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.neo.yandexpvz.worker.DatabaseSyncWorker
import com.yandex.mapkit.MapKitFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.concurrent.TimeUnit


@AndroidEntryPoint
@ExperimentalMaterialApi
@ExperimentalAnimationApi
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        MapKitFactory.initialize(this)
        val workRequest = PeriodicWorkRequestBuilder<DatabaseSyncWorker>(6, TimeUnit.HOURS)
            .addTag("yandexWork")
            .build()
        WorkManager.getInstance(applicationContext).enqueueUniquePeriodicWork("yandexWork",
            ExistingPeriodicWorkPolicy.KEEP, workRequest)

        setContent {
                 YandexApp()
        }
    }
}
