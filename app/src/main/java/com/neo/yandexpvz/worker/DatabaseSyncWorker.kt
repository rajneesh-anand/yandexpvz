package com.neo.yandexpvz.worker

import android.content.Context
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.utils.TokenManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



@HiltWorker
class DatabaseSyncWorker @AssistedInject constructor(
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters,
    private val coinRepository: CoinRepository,
    private val tokenManager: TokenManager
) :
    CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        CoroutineScope(Dispatchers.IO).launch {
            coinRepository.insertCoins(tokenManager.getUserMobile().toString())
        }

        return Result.success()
    }
}