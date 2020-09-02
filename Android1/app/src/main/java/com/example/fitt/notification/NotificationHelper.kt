package com.example.fitt.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.example.fitt.MainActivity
import com.example.fitt.R
import com.example.fitt.fragments.KEY_ID
import com.example.fitt.repository.ReminderData
import com.example.fitt.repository.WorkoutType

//Special object for manage creation of notification channel and data for notification
object NotificationHelper {

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
            setSmallIcon(R.drawable.ic_fitt_sample) // 3 - The only required parameter when creating a notification is the icon.
            setContentTitle(title) // 4 - title
            setContentText(message) // 5 - message
            setAutoCancel(autoCancel) // 6 - Setting this flag will make it so the notification is automatically * canceled when the user clicks it in the panel.
            setStyle(NotificationCompat.BigTextStyle().bigText(bigText)) // 7
            priority = NotificationCompat.PRIORITY_DEFAULT // 8
            color = Color.GREEN

            // Create intent and PendingIntent to open main screen
            val intent = Intent(context, MainActivity::class.java) // 1 - Intent create
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK // 2 - Set flags for the launchMode in which the application will be launched
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0) // 3 - We wrap the Intent in a PendingIntent, using the getActivity () method to get the Activity to be started.
            setContentIntent(pendingIntent) // 4 - We call the setContentIntent () method to pass the created PendingIntent to the NotificationCompat.Builder to call it when the user clicks on the notification.
        }

        /**
        Here we get a link to the NotificationManagerCompat and call the notify () method to display a notification,
        where 1001 is just a certain id, which is required, and notificationBuilder.build (), as a result,
        creates a notification that we have carefully constructed. */
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1001, notificationBuilder.build())
    }

    fun createNotificationForWorkout(context: Context, reminderData: ReminderData) {

        // 1 create a group notification
        val groupBuilder = buildGroupNotification(context, reminderData)
        // 2
        val notificationBuilder = buildNotification(context, reminderData)
        // 3
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(reminderData.type.ordinal, groupBuilder.build())
        notificationManager.notify(reminderData.id.toInt(), notificationBuilder.build())
    }
    //build single notification
    private fun buildGroupNotification(
            context: Context,
            reminderData: ReminderData
    ): NotificationCompat.Builder {

        val channelId = "${context.packageName}-${reminderData.type.name}"

        //result
        return NotificationCompat.Builder(context, channelId).apply {
            setSmallIcon(R.drawable.ic_fitnes)
            setContentTitle(reminderData.type.name)
            setContentText(
                    context.getString(
                            R.string.group_notification_for,
                            reminderData.type.name
                    )
            )
            setStyle(
                    NotificationCompat.BigTextStyle().bigText(
                            context.getString(
                                    R.string.group_notification_for,
                                    reminderData.type.name
                            )
                    )
            )
            setAutoCancel(true)
            setGroupSummary(true)
            setGroup(reminderData.type.name)
        }
    }

    //fun to build no notification with NotificationCompat.Builder and return build notification as a result
    private fun buildNotification(
            context: Context,
            reminderData: ReminderData
    ): NotificationCompat.Builder {
        // 1 - create unique channel ID for that we are using package name of app
        val channelId = "${context.packageName}-${reminderData.type.name}"

        // 2 - for creating notification using NotificationCompat.Builder + scope fun .apply
        return NotificationCompat.Builder(context, channelId).apply {

            //BUILD notification
            setSmallIcon(R.drawable.ic_fitnes)// 1 - The only required parameter when creating a notification is the icon.
            setContentTitle(reminderData.name)// 2 - title
            setAutoCancel(true)// 3 - Setting this flag will make it so the notification is automatically * canceled when the user clicks it in the panel.

            // get a drawable reference for the LargeIcon
            val drawable = when (reminderData.type) {
                WorkoutType.Running -> R.drawable.ic_run
                WorkoutType.Cycling -> R.drawable.ic_bicycle
                else -> R.drawable.ic_swimming
            }
            setLargeIcon(BitmapFactory.decodeResource(context.resources, drawable)) // 4 - set large icon
            setContentText("${reminderData.name}") // 5 - set content text
            setGroup(reminderData.type.name) // 6 - set group name

            // Launches the app to open the reminder edit screen when tapping the whole notification. // 1 - Intent create, 2 - set flags to launchMode
            val intent = Intent(context, MainActivity::class.java).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                putExtra(KEY_ID, reminderData.id)
            }
            // 3 - then wrap with PendingIntent
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, 0)
            setContentIntent(pendingIntent) // 7 - will take care of opening activity when notification clicked

        }
    }
}
