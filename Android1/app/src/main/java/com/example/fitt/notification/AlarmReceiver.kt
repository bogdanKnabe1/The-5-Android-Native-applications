package com.example.fitt.notification

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.example.fitt.FittApp
import com.example.fitt.database.WorkoutDatabase
import com.example.fitt.repository.ReminderRepository
import com.example.fitt.utils.KEY_ID

//Alarm receiver is Broadcast, implementing onReceive fun which one is activated when it get message from system.
//and execute code in fun onReceive()
class AlarmReceiver : BroadcastReceiver() {
    private val TAG = AlarmReceiver::class.java.simpleName

    private val repository: ReminderRepository

    init {
        val userDao = WorkoutDatabase.getDatabase(
            FittApp.applicationContext()
        ).reminderDataDao()
        repository = ReminderRepository(userDao)
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d(TAG, "onReceive() called with: context = [$context], intent = [$intent]")
        if (context != null && intent != null) {
            if (intent.extras != null) {

                val reminderData = repository.getReminderById(intent.extras!!.getLong(KEY_ID))

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationHelper.createNotificationForWorkout(context, reminderData)
                } else {
                    NotificationHelper.createNotificationForOldDevices(context, reminderData)
                }

            }
        }
    }


}
