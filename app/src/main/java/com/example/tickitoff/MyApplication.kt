package com.example.tickitoff

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

// Class that inherits from Application, used for creating our notification channel

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // Only on Android 8 (oreo) and above
            val notificationChannel = NotificationChannel(
                "tickitoff_notification_channel",
                "TickItOffChannel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                enableLights(true)
                lightColor =
                    Color.Blue.toArgb() // We will flash our custom color so user gets a hint it comes from our app
            }
            notificationChannel.description =
                "For the TickItOff app's reminder notifications" // User can see this text in settings

            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }
}