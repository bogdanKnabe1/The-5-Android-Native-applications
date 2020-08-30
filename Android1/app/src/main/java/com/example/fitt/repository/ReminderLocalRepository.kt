package com.example.fitt.repository

import android.content.Context
import com.example.fitt.FittApp

class ReminderLocalRepository(val context: Context?) {

    private val roomDatabase = (context?.applicationContext as FittApp).getDatabase()
    private val dao = roomDatabase.reminderDataDao()

    fun getReminders(): List<ReminderData> {
        return dao.getReminderData()
    }

    fun saveReminder(reminderData:ReminderData):Long{
        return dao.insert(reminderData)
    }
}