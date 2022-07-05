package com.example.instagramproject.editorStory

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.content.getSystemService

/**
 * Created by Burhanuddin Rashid on 1/23/2018.
 */
class PhotoApp : Application() {

    var NOTFICATION_CHANEL = "ASD"
    var notyChanel = NotificationChannel(
        NOTFICATION_CHANEL,
        "ASDSAD",
        NotificationManager.IMPORTANCE_HIGH
    )

    override fun onCreate() {
        super.onCreate()
        photoApp = this
        notifcation()
    }

    companion object {
        var photoApp: PhotoApp? = null
        var NOTFICATION_CHANEL = "ASD"

        private val TAG = PhotoApp::class.java.simpleName
        var manager: NotificationManager? = null
    }

    fun notifcation() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(notyChanel)
        } else {
            manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        }
    }


}