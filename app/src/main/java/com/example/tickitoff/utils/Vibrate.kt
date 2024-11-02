package com.example.tickitoff.utils

import android.content.Context
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator

// To enable vibration of the device for more interactive feedback, used for when clicking "complete" on a goal atm

fun vibrateDevice(context: Context) {
    val vibrator = context.getSystemService(Vibrator::class.java) // Get via getsystemservice
    if (vibrator.hasVibrator()) { // Checks to ensure the device actually has a vibrating part
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) { // This constant represents API level 26 (Android 8.0 Oreo)
            // Use VibrationEffect for API 26+
            vibrator.vibrate(
                VibrationEffect.createOneShot(
                    500, // half a second length
                    VibrationEffect.DEFAULT_AMPLITUDE
                )
            )
        } else {
            // Use the deprecated method for devices running API < 26
            @Suppress("DEPRECATION") vibrator.vibrate(500) // half a second length
        }
    }
}