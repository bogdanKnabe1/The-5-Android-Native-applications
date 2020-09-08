package com.example.fitt

import android.app.Application
import android.content.Context
import androidx.core.app.NotificationManagerCompat
import com.example.fitt.notification.NotificationHelper
import com.example.fitt.utils.WorkoutType

//SINGLETON
class FittApp : Application() {

    init {
        instance = this
    }

    companion object {
        private var instance: FittApp? = null

        fun applicationContext() : Context {
            return instance!!.applicationContext
        }
    }

    //Runs only one time at first startup of application
    override fun onCreate() {
        super.onCreate()
        // Use ApplicationContext.
        // example: SharedPreferences etc...

        /* Please note that when creating a notification channel,
        you must specify the so-called level of importance of the channel.
        It depends on how exactly the message from such a channel will appear (for example, with or without sound).

        NotificationManagerCompat.IMPORTANCE_LOW - Low level of importance, shown everywhere (both in the curtain and in the status bar), but without sound
        NotificationManagerCompat.IMPORTANCE_HIGH - The highest level of importance, such notifications are shown with sound and pop up on the screen
        NotificationManagerCompat.IMPORTANCE_NONE - No importance. Such notifications are not shown*/

        // 1 channel for Cycling
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_HIGH, true,
                WorkoutType.Cycling.name, "Notification channel for Cycling."
        )
        // 2 channel for Swimming
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_HIGH, true,
                WorkoutType.Swimming.name, "Notification channel for Swimming."
        )
        // 3 channel for Running
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_HIGH, false,
                WorkoutType.Running.name, "Notification channel for other Running"
        )
    }

}