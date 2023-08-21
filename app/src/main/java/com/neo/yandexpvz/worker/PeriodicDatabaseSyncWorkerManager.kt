package com.neo.yandexpvz.worker

import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit
import javax.inject.Inject



class PeriodicDatabaseSyncWorkManager @Inject constructor(

    private val workManager: WorkManager
) {
    private val WORK_TAG = "yandexWork"
    private val repeatInterval = 10
    private val repeatIntervalTimeUnit: TimeUnit = TimeUnit.MINUTES

    fun startPeriodicNotifications() {
        val workRequest = PeriodicWorkRequestBuilder<DatabaseSyncWorker>(
            10,
            repeatIntervalTimeUnit
        )
            .addTag(WORK_TAG)
            .build()

        workManager.enqueueUniquePeriodicWork(
            WORK_TAG,
            ExistingPeriodicWorkPolicy.KEEP,
            workRequest
        )
    }
}