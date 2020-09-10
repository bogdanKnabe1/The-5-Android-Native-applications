package com.example.fitt.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.fitt.database.WorkoutDatabase
import com.example.fitt.database.entity.ReminderData
import com.example.fitt.repository.ReminderRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ReminderViewModel(application: Application): AndroidViewModel(application) {

    val readAllData: LiveData<List<ReminderData>>
    private val repository: ReminderRepository

    init {
        val userDao = WorkoutDatabase.getDatabase(
            application
        ).reminderDataDao()
        repository = ReminderRepository(userDao)
        readAllData = repository.readAllData
    }

    fun insertReminder(reminderData: ReminderData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.insertReminderRepository(reminderData)
        }
    }

    fun updateReminder(reminderData: ReminderData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateReminderRepository(reminderData)
        }
    }

    fun deleteReminder(reminderData: ReminderData){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteByIdReminderRepository(reminderData)
        }
    }

    fun deleteAllReminders(){
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteAllReminderRepository()
        }
    }
}