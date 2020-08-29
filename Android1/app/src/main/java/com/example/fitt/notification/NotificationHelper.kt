package com.example.fitt.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitt.MainActivity
import com.example.fitt.R

//Special object for manage creation of notification channel and data for notification
object  NotificationHelper {

    /**
     * Sets up the notification channels for API 26+.
     * Note: This uses package name + channel name to create unique channelId's.
     *
     * @param context     application context
     * @param importance  importance level for the notificaiton channel
     * @param showBadge   whether the channel should have a notification badge
     * @param name        name for the notification channel
     * @param description description for the notification channel
     */

    fun createNotificationChannel(
            context: Context,
            importance: Int,
            showBadge: Boolean,
            name: String,
            description: String
    ) {

        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            val channelId = "${context.packageName}-$name"
            val channel = NotificationChannel(channelId, name, importance)
            channel.description = description
            channel.setShowBadge(showBadge)

            // Register the channel with the system
            val notificationManager = context.getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    /**
     * Helps issue the default application channels (package name + app name) notifications.
     * Note: this shows the use of [NotificationCompat.BigTextStyle] for expanded notifications.
     *
     * @param context    current application context
     * @param title      title for the notification
     * @param message    content text for the notification when it's not expanded
     * @param bigText    long form text for the expanded notification
     * @param autoCancel `true` or `false` for auto cancelling a notification.
     * if this is true, a [PendingIntent] is attached to the notification to
     * open the application.
     */
    fun createSampleDataNotification(
            context: Context, title: String, message: String,
            bigText: String, autoCancel: Boolean
    ) {

        // 1 - create unique channel ID for that we are using package name of app
        val channelId = "${context.packageName}-${context.getString(R.string.app_name)}"
        // 2 - for creating notification using NotificationCompat.Builder + scope fun .apply
        val notificationBuilder = NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_fitnes) // 3 - The only required parameter when creating a notification is the icon.
            setContentTitle(title) // 4 - title
            setContentText(message) // 5 - message
            setAutoCancel(autoCancel) // 6 - Setting this flag will make it so the notification is automatically * canceled when the user clicks it in the panel.
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText)) // 7
            priority = NotificationCompat.PRIORITY_DEFAULT // 8

            val intent = Intent(context, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent)
        }

        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())
    }
}
