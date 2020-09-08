package com.example.fitt.repository

import android.content.Context
import com.example.fitt.FittApp
import com.example.fitt.database.entity.ReminderData

class ReminderLocalRepository(val context: Context?) {

    private val roomDatabase = (context?.applicationContext as FittApp).getDatabase()
    private val dao = roomDatabase.reminderDataDao()

    //to save data in database
    fun saveReminder(reminderData: ReminderData):Long{
        return dao.insert(reminderData)
    }

    //to get ALL data in list
    fun getReminders(): MutableList<ReminderData> {
        return dao.getReminderData()
    }
    //to get data by ID
    fun getReminderById(id: Long): ReminderData? {
        return dao.getReminderById(id)
    }

    //to delete all reminders
    fun deleteAll() {
        return dao.deleteAll()
    }

    //to delete special reminder
    fun deleteById(reminderData: ReminderData?) {
        return dao.deleteById(reminderData?.id)
    }
}