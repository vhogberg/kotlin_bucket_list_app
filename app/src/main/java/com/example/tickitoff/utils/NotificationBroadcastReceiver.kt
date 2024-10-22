package com.example.tickitoff.utils

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

// Broadcast receiver to enable notifications to be scheduled

class NotificationBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val title = intent.getStringExtra("title") ?: "Are you feeling inspired?"
        val message = intent.getStringExtra("message") ?: "Today is a great day to set a new goal in the TickItOff app!"

        val notificationHandler = NotificationHandler(context)
        notificationHandler.showNotification(title, message)
    }
}