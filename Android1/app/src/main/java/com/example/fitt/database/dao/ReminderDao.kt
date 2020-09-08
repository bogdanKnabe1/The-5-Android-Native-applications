package com.example.fitt.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.fitt.database.entity.ReminderData

@Dao
interface ReminderDao {

    //insert dao
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminder: ReminderData): Long

    //update dao
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(reminder: ReminderData)

    @Query("SELECT * from reminder_data")
    fun getReminderData(): LiveData<List<ReminderData>>

    @Query("SELECT * from reminder_data where id= :id")
    fun getReminderById(id: Long): ReminderData

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminders: List<ReminderData>)*/

    @Query("DELETE FROM reminder_data where id LIKE :id")
    suspend fun deleteById(id: Long)

    @Query("DELETE FROM reminder_data")
    suspend fun deleteAll()


}