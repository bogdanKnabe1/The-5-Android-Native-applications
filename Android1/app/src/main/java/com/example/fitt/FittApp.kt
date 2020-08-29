package com.example.fitt

import android.app.Application
import androidx.core.app.NotificationManagerCompat
import com.example.fitt.notification.NotificationHelper


class FittApp : Application() {

    override fun onCreate() {
        super.onCreate()
        NotificationHelper.createNotificationChannel(
                this,
                NotificationManagerCompat.IMPORTANCE_DEFAULT, false,
                getString(R.string.app_name), "Канал Фитнес - приложения."
        )

    }
}