package com.example.final_project

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        createNotificationChannels()
    }

    private fun createNotificationChannels() {
        val channels = listOf(
            NotificationChannel(
                "deliveryNotificationChannel",
                "Delivery Updates",
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply { description = "Notification for ongoing delivery" },
            NotificationChannel(
                "ChatMessageNotificationChannel",
                "Chat Updates",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Notification for chat message"
            })
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannels(channels)
    }
}