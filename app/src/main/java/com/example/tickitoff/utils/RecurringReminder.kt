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

    // If the time is in the past, add one week
    if (calendar.timeInMillis < System.currentTimeMillis()) {
        calendar.add(Calendar.WEEK_OF_YEAR, 1)
    }

    // Define a weekly interval (7 days in milliseconds)
    val weeklyInterval = 7 * AlarmManager.INTERVAL_DAY

    // Schedule the alarm to repeat weekly
    alarmManager.setInexactRepeating(
        AlarmManager.RTC_WAKEUP, calendar.timeInMillis, weeklyInterval,  // Repeat weekly
        pendingIntent
    )
}