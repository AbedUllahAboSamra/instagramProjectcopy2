package com.example.instagramproject.Service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.instagramproject.R
import com.example.instagramproject.editorStory.PhotoApp
import com.example.instagramproject.screen.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class FireBaseMassagingServices() : FirebaseMessagingService() {


    override fun onMessageReceived(message: RemoteMessage) {
        Log.e("ASD","SSSSSSSSSSSSSSSS")
        var title = message.notification!!.title
        var body = message.notification!!.body
        var notfivation = Notification.Builder(this, PhotoApp.NOTFICATION_CHANEL)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setAutoCancel(true)
            .setContentIntent(
                PendingIntent.getActivity(
                    applicationContext,
                    0,
                    Intent(applicationContext, MainActivity::class.java),
                    1
                )
            )
            .build()
        PhotoApp.manager!!.notify(0, notfivation)
        super.onMessageReceived(message)
    }

}