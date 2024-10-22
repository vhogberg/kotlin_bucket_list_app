package com.example.tickitoff.utils

import android.app.NotificationManager
import android.content.Context
import android.util.Log
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.NotificationCompat
import com.example.tickitoff.R
import com.example.tickitoff.ui.theme.CustomGreen
import kotlin.random.Random

// Handling notifications with NotificationManager

class NotificationHandler(
    private val context: Context
) {
    private val notificationManager = context.getSystemService(NotificationManager::class.java)
    private val notificationChannelID = "tickitoff_notification_channel"

    // Method to show simple notification, takes in a title and message and flashes LED if there is one
    fun showNotification(title: String, message: String) {
        val notification = NotificationCompat.Builder(context, notificationChannelID)
            .setContentTitle(title)
            .setContentText(message)
            .setSmallIcon(R.drawable.tickitofflogo)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setLights(
                CustomGreen.toArgb(),
                500,
                500
            ) // We will flash our custom color so user gets a hint it comes from our app
            .setAutoCancel(true)
            .build()

        // Random id
        notificationManager.notify(Random.nextInt(), notification)
        Log.d("NotificationHandler", "Här flashas LED")
    }
}