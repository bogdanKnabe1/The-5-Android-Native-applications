package com.example.fitt.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.fitt.R
import com.example.fitt.database.entity.ReminderData
import com.example.fitt.utils.KEY_ID
import java.util.*

object AlarmScheduler {
    /**
     * Schedules all the alarms for [ReminderData].
     *
     * @param context      current application context
     * @param reminderData ReminderData to use for the alarm
     */
    fun scheduleAlarmsForReminder(context: Context, reminderData: ReminderData) {

        // get the AlarmManager reference
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        // Schedule the alarms based on the days to administer the medicine
        val days = context.resources.getStringArray(R.array.days)
        if (reminderData.days != null) {
            for (index in reminderData.days!!.indices) {

                val day = reminderData.days!![index]
                if (day != null) {

                    // get the PendingIntent for the alarm
                    val alarmIntent = createPendingIntent(context, reminderData, day)

                    // schedule the alarm
                    val dayOfWeek = getDayOfWeek(days, day)
                    scheduleAlarm(reminderData, dayOfWeek, alarmIntent, alarmManager)
                }
            }
        }
    }

    /**
     * Schedules a single alarm
     */
    private fun scheduleAlarm(reminderData: ReminderData, dayOfWeek: Int, alarmIntent: PendingIntent?, alarmManager: AlarmManager) {

        // Set up the time to schedule the alarm
        val datetimeToAlarm = Calendar.getInstance(Locale.getDefault())
        datetimeToAlarm.timeInMillis = System.currentTimeMillis()
        datetimeToAlarm.set(Calendar.HOUR_OF_DAY, reminderData.hour)
        datetimeToAlarm.set(Calendar.MINUTE, reminderData.minute)
        datetimeToAlarm.set(Calendar.SECOND, 0)
        datetimeToAlarm.set(Calendar.MILLISECOND, 0)
        datetimeToAlarm.set(Calendar.DAY_OF_WEEK, dayOfWeek)

        // Compare the datetimeToAlarm to today
        val today = Calendar.getInstance(Locale.getDefault())
        if (shouldNotifyToday(dayOfWeek, today, datetimeToAlarm)) {

            // schedule for today
            alarmManager.setRepeating(
                    AlarmManager.RTC_WAKEUP,
                    datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
            return
        }

        // schedule 1 week out from the day
        datetimeToAlarm.roll(Calendar.WEEK_OF_YEAR, 1)
        alarmManager.setRepeating(
                AlarmManager.RTC_WAKEUP,
                datetimeToAlarm.timeInMillis, (1000 * 60 * 60 * 24 * 7).toLong(), alarmIntent)
    }

    /**
     * Creates a [PendingIntent] for the Alarm using the [ReminderData]
     *
     * @param context      current application context
     * @param reminderData ReminderData for the notification
     * @param day          String representation of the day
     */
    private fun createPendingIntent(context: Context, reminderData: ReminderData, day: String?): PendingIntent? {
        // 1 - Create an Intent and specify AlarmReceiver :: class.java as the destination
        val intent = Intent(context.applicationContext, AlarmReceiver::class.java).apply {
            // 2 Let's set the type - the type must be unique - and consists of the name of the reminder and the type of training.
            // If the type is not unique, then such an intent will replace another PendingIntent with the same type.
            type = "$day-${reminderData.name}-${reminderData.type.name}"
            // 3 We add the ID of the ReminderData object to the additional parameters of the Intent
            // so that it can then be used in the AlarmReceiver to get data about the time and date of the training.
            putExtra(KEY_ID, reminderData.id)
        }
        // 4 - Create a PendingIntent using the getBroadcast () method.
        // This is very important since we are creating an Intent with a BroadcastReceiver as the receiver of such an intent.
        return PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
    }

    /**
     * Determines if the Alarm should be scheduled for today.
     *
     * @param dayOfWeek day of the week as an Int
     * @param today today's datetime
     * @param datetimeToAlarm Alarm's datetime
     */
    private fun shouldNotifyToday(dayOfWeek: Int, today: Calendar, datetimeToAlarm: Calendar): Boolean {
        return dayOfWeek == today.get(Calendar.DAY_OF_WEEK) &&
                today.get(Calendar.HOUR_OF_DAY) <= datetimeToAlarm.get(Calendar.HOUR_OF_DAY) &&
                today.get(Calendar.MINUTE) <= datetimeToAlarm.get(Calendar.MINUTE)
    }

    /**
     * Returns the int representation for the day of the week.
     *
     * @param days      array from resources
     * @param dayOfWeek String representation of the day e.g "Sunday"
     * @return [Calendar.DAY_OF_WEEK] for given dayOfWeek
     */
    private fun getDayOfWeek(days: Array<String>, dayOfWeek: String): Int {
        return when {
            dayOfWeek.equals(days[0], ignoreCase = true) -> Calendar.MONDAY
            dayOfWeek.equals(days[1], ignoreCase = true) -> Calendar.TUESDAY
            dayOfWeek.equals(days[2], ignoreCase = true) -> Calendar.WEDNESDAY
            dayOfWeek.equals(days[3], ignoreCase = true) -> Calendar.THURSDAY
            dayOfWeek.equals(days[4], ignoreCase = true) -> Calendar.FRIDAY
            dayOfWeek.equals(days[5], ignoreCase = true) -> Calendar.SATURDAY
            dayOfWeek.equals(days[6], ignoreCase = true) -> Calendar.SUNDAY
            else -> Calendar.MONDAY
        }
    }

}
