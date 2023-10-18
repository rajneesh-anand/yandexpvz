package com.neo.yandexpvz.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.neo.yandexpvz.repository.CoinRepository
import com.neo.yandexpvz.utils.NetworkUtils
import com.neo.yandexpvz.utils.TokenManager
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch



@HiltWorker
class DatabaseSyncWorker @AssistedInject constructor(
    private val coinRepository: CoinRepository,
    private val tokenManager: TokenManager,
    @Assisted val context: Context,
    @Assisted val workerParams: WorkerParameters
):CoroutineWorker(context, workerParams) {
    override suspend fun doWork(): Result {
        val mobileNumber = tokenManager.getUserMobile().toString()
        Log.d("yandexWork mobile" , mobileNumber)
        mobileNumber?.let {
            CoroutineScope(Dispatchers.IO).launch {
               coinRepository.insertCoins(it)
            }
        }
        return Result.success()
    }
}