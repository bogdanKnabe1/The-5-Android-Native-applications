package com.example.fitt

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.fitt.database.WorkoutDatabase
import com.example.fitt.notification.NotificationHelper
import com.example.fitt.utils.WorkoutType

//SINGLETON
class FittApp : Application() {

    lateinit var app: FittApp
    private lateinit var database: WorkoutDatabase

    //Runs only one time at first startup of application
    override fun onCreate() {
        super.onCreate()
        app = this
        database = Room.databaseBuilder(this, WorkoutDatabase::class.java, "database")

        /*Method .allowMainThreadQueries () - allows accessing the database in the main thread,
        in a real application this should be avoided - since read operations from the database can be long and you will get ANR
        Change on COROUTINE */
                .allowMainThreadQueries()
                //DESTRUCT MIGRATION
                .fallbackToDestructiveMigration()
                .build()

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

    fun getInstance(): FittApp {
        return app
    }

   fun getDatabase(): WorkoutDatabase {
        return database
    }
}