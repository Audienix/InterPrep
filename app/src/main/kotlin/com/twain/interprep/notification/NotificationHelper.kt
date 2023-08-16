package com.twain.interprep.notification

import android.Manifest
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.twain.interprep.MainActivity
import com.twain.interprep.R
import com.twain.interprep.constants.NumberConstants.PENDING_INTENT_REQUEST_CODE

class NotificationHelper(val context: Context) {

    private val ipNotificationChannel = "interprep_channel_id"
    private val notificationId = 1

    fun createNotification(title: String, message: String){
        createNotificationChannel()
        val intent = Intent(context, MainActivity:: class.java).apply{
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            PENDING_INTENT_REQUEST_CODE,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
        val notification = NotificationCompat.Builder(context, ipNotificationChannel)
            .setSmallIcon(R.drawable.ic_app_notification)
            .setColor(context.resources.getColor(R.color.primary,null))
//            .setLargeIcon(icon)
            .setContentTitle(title)
            .setContentText(message)
//            .setStyle(
//                NotificationCompat.BigPictureStyle().bigPicture(icon)
//            )
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .build()

        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return

        NotificationManagerCompat.from(context).notify(notificationId, notification)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                ipNotificationChannel,
                ipNotificationChannel,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                description = "InterPrep Reminder Channel Description"
            }
            val notificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}
