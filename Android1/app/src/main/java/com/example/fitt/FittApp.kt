package com.example.fitt

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.fitt.notification.NotificationHelper
import com.example.fitt.repository.WorkoutDatabase
import com.example.fitt.repository.WorkoutType


class FittApp : Application() {

    lateinit var app: FittApp
    private lateinit var database: WorkoutDatabase

    override fun onCreate() {
        super.onCreate()
        app = this
        database = Room.databaseBuilder(this, WorkoutDatabase::class.java, "database")

        /*Method .allowMainThreadQueries () - allows accessing the database in the main thread,
        in a real application this should be avoided - since read operations from the database can be long and you will get ANR
        Change on COROUTINE */
                .allowMainThreadQueries()
                .build()

        // 1
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_LOW, true,
                WorkoutType.Cycling.name, "Notification channel for Cycling."
        )
        // 2
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_HIGH, true,
                WorkoutType.Swimming.name, "Notification channel for Swimming."
        )
        // 3
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_NONE, false,
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