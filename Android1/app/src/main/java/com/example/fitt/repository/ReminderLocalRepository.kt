package com.example.fitt.repository

import android.content.Context
import com.example.fitt.FittApp

class ReminderLocalRepository(val context: Context?) {

    private val roomDatabase = (context?.applicationContext as FittApp).getDatabase()
    private val dao = roomDatabase.reminderDataDao()
    //to get ALL data in list
    fun getReminders(): List<ReminderData> {
        return dao.getReminderData()
    }
    //to get data by ID
    fun getReminderById(id: Long): ReminderData? {
        return dao.getReminderById(id)
    }
    //to save data in database
    fun saveReminder(reminderData:ReminderData):Long{
        return dao.insert(reminderData)
    }

    fun deleteById(reminderData: ReminderData) {
        return dao.deleteById(reminderData.id)
    }

    fun deleteAll() {
        return dao.deleteAll()
    }
}