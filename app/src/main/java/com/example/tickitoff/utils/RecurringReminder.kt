package com.example.tickitoff.utils

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.Calendar

// Method for recurring notification reminders for the user to encourage them to set new goals

fun scheduleRecurringReminder(context: Context) {
    // Uses AlarmManager for scheduling
    val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    val intent = Intent(context, NotificationBroadcastReceiver::class.java).apply {
        putExtra("title", "Are you feeling inspired?")
        putExtra("message", "Today is a great day to set a new goal in the TickItOff app!")
    }

    val pendingIntent = PendingIntent.getBroadcast(
        context,
        0,
        intent,
        PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE // FLAG_IMMUTABLE for API level 31+
    )

    // Set the time for the reminder
    val calendar = Calendar.getInstance().apply {
        timeInMillis = System.currentTimeMillis()
        set(Calendar.HOUR_OF_DAY, 10)  // Set the hour (10 AM)
        set(Calendar.MINUTE, 0) // Set the minute (0 minutes)
        set(Calendar.SECOND, 0) // Set seconds to 0
    }

    // For testing
    //    val calendar = Calendar.getInstance().apply {
    //        timeInMillis = System.currentTimeMillis()
    //        add(Calendar.MINUTE, 2)  // Schedule the notification for 2 minutes from now for testing
    //    }

    // If the time is in the past then add one day
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.DAY_OF_YEAR, 1)
    }

    // Schedule the alarm
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP,
        calendar.timeInMillis,
        AlarmManager.INTERVAL_DAY,  // Repeat daily
        pendingIntent
    )
}
