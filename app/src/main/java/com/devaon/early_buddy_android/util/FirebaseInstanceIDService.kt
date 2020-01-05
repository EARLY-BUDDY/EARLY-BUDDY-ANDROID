package com.devaon.early_buddy_android.util

import android.os.Build
import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import android.app.NotificationChannel
import android.content.Context.NOTIFICATION_SERVICE
import androidx.core.content.ContextCompat.getSystemService
import android.app.NotificationManager
import android.content.Context
import android.R.id.message
import android.R
import android.app.Notification
import androidx.core.app.NotificationCompat
import com.devaon.early_buddy_android.data.login.Login


public class FirebaseInstanceIDService : FirebaseMessagingService() {
    override fun onNewToken(p0: String?) {
        super.onNewToken(p0)

        Log.e("Firebase", "Firebase Instance IDService : " + p0)
        if(p0 != null)
            Login.setDeviceToken(this, p0)
    }

    override fun onMessageReceived(p0: RemoteMessage?) {
        if(p0 != null && p0.data.size > 0){
            sendNotification(p0)
        }
    }

    private fun sendNotification(remoteMessage: RemoteMessage){
        var title = remoteMessage.data.get("title")
        var message = remoteMessage.data.get("message")

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = "채널"
            val channel_nm ="채널명"

            val notichannel =
                getSystemService(Context.NOTIFICATION_SERVICE) as android.app.NotificationManager
            val channelMessage = NotificationChannel(
                channel, channel_nm,
                android.app.NotificationManager.IMPORTANCE_DEFAULT
            )
            channelMessage.description = "채널에 대한 설명."
            channelMessage.enableLights(true)
            channelMessage.enableVibration(true)
            channelMessage.setShowBadge(false)
            channelMessage.vibrationPattern = longArrayOf(100, 200, 100, 200)
            notichannel.createNotificationChannel(channelMessage)

            val notificationBuilder = NotificationCompat.Builder(this, channel)
                .setSmallIcon(R.drawable.ic_menu_gallery)
                .setContentTitle(title)
                .setContentText(message)
                .setChannelId(channel)
                .setAutoCancel(true)
                .setDefaults(Notification.DEFAULT_SOUND or Notification.DEFAULT_VIBRATE)

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            notificationManager.notify(9999, notificationBuilder.build())

        }
    }
}