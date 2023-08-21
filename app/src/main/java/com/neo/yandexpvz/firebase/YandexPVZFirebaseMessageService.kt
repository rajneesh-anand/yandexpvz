package com.neo.yandexpvz.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.core.app.NotificationCompat
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.neo.yandexpvz.MainActivity
import com.neo.yandexpvz.R
import com.neo.yandexpvz.api.RestApi
import com.neo.yandexpvz.db.CoinDao
import com.neo.yandexpvz.repository.dataStore
import com.neo.yandexpvz.utils.TokenManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.random.Random
//
//@SuppressLint("MissingPermission")
//class YandexPVZFirebaseMessageService : FirebaseMessagingService() {
//    override fun onMessageReceived(message: RemoteMessage) {
//        super.onMessageReceived(message)
//        sendNotification(message)
//        Log.d("SEND-MESSAGE","$")
//    }
//
//    private fun sendNotification(message: RemoteMessage) {
//        Log.d("TITLE", "${message.notification}")
//        if (message.notification != null) {
//            val title = message.notification?.title.toString()
//            val body = message.notification?.body.toString()
//            Log.d("TITLE-TITLE","$title")
//            Log.d("TITLE-BODY","$body")
//
//            val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH)
//            getSystemService(NotificationManager::class.java).createNotificationChannel(channel)
//            val notification = Notification.Builder(this, CHANNEL_ID)
//                .setContentTitle(title)
//                .setContentText(body)
//                .setSmallIcon(android.R.drawable.ic_menu_manage)
//                .setColor(getColor(R.color.purple_700))
//                .setAutoCancel(true)
//                .build()
//            NotificationManagerCompat.from(this).notify(1, notification)
//        }
//        if (message.data.isNotEmpty()) {
//            val bookId = message.data[BOOK_ID]
//            Log.d("BOOKID", "$bookId")
//        }
//    }
//
//    override fun onNewToken(token: String) {
//        Log.d("TOKENID", "$token")
//    }
//}
//


class YandexPVZFirebaseMessageService : FirebaseMessagingService() {

    private val random = Random
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.notification?.let { message ->
            sendNotification(message)
        }
    }

    @OptIn(ExperimentalMaterialApi::class, ExperimentalAnimationApi::class)
    private fun sendNotification(message: RemoteMessage.Notification) {
        // If you want the notifications to appear when your app is in foreground

        val intent = Intent(this, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        }

        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )

        val channelId = this.getString(R.string.default_notification_channel_id)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(message.title)
            .setContentText(message.body)
            .setSmallIcon(R.drawable.coin)
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)

        val manager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, CHANNEL_NAME,
                NotificationManager.IMPORTANCE_DEFAULT
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(random.nextInt(), notificationBuilder.build())
    }

    override fun onNewToken(token: String) {
        // If you want to send messages to this application instance or
        // manage this apps subscriptions on the server side, send the
        // FCM registration token to your app server.
        super.onNewToken(token)
        GlobalScope.launch { saveFCMToken(token) }
        Log.d("FCM-TOKEN","New token: $token")
//        tokenManager.fcmToken(token)



    }

    private suspend fun saveFCMToken(token: String) {
        val fcmTokenkey = stringPreferencesKey("fcm_token")
        baseContext.dataStore.edit { pref ->
            pref[fcmTokenkey]= token
        }
    }

    companion object {
        const val CHANNEL_NAME = "FCM notification channel"
    }
}


