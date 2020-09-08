package com.example.fitt.repository

import androidx.lifecycle.LiveData
import com.example.fitt.database.dao.ReminderDao
import com.example.fitt.database.entity.ReminderData

class ReminderRepository(private val dao: ReminderDao) {

    //get ALL DATA
    val readAllData: LiveData<List<ReminderData>> = dao.getReminderData()

    //to save data in database
    suspend fun insertReminderRepository(reminderData: ReminderData): Long {
        return dao.insert(reminderData)
    }

    suspend fun updateReminderRepository(reminderData: ReminderData) {
        dao.update(reminderData)
    }
    /*//to get ALL data in list old list
    fun getReminders(): MutableList<ReminderData> {
        return dao.getReminderData()
    }*/

    //to get data by ID
    fun getReminderById(id: Long): ReminderData {
        return dao.getReminderById(id)
    }

    //to delete all reminders
    suspend fun deleteAllReminderRepository() {
        return dao.deleteAll()
    }

    //to delete special reminder
    suspend fun deleteByIdReminderRepository(reminderData: ReminderData) {
        return dao.deleteById(reminderData.id)
    }
}