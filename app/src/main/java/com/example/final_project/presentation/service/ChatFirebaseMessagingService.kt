package com.example.final_project.presentation.service

import android.Manifest
import android.app.PendingIntent
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.example.final_project.R
import com.example.final_project.presentation.activity.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class ChatFirebaseMessagingService: FirebaseMessagingService() {

    override fun onMessageReceived(message: RemoteMessage) {
        super.onMessageReceived(message)
        showNotification(message.data["imageUrl"] ?: "", message.data["name"] ?: "", message.data["id"] ?: "0")
    }

    private fun showNotification(imageUrl: String, name: String, id: String) {
        val intent = Intent(this, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            putExtra("id", id)
            putExtra("name", name)
            putExtra("imageUrl", imageUrl)
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "ChatMessageNotificationChannel"
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setContentTitle("New Message")
            .setContentText("You got new Message from $name")
            .setSmallIcon(R.drawable.ic_delivery)
            .setContentIntent(pendingIntent)
            .setStyle(NotificationCompat.BigTextStyle())
            .build()

        if (ContextCompat.checkSelfPermission(applicationContext, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED) {
            NotificationManagerCompat.from(applicationContext).notify(2, notificationBuilder)
        }
    }

    override fun onNewToken(token: String) {
        super.onNewToken(token)
    }
}