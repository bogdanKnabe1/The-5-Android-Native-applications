package com.example.fitt.database.dao

import androidx.room.*
import com.example.fitt.database.entity.ReminderData

@Dao
interface ReminderDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: ReminderData): Long

    @Query("SELECT * from reminder_data")
    fun getReminderData(): MutableList<ReminderData>

    @Query("SELECT * from reminder_data where id= :id")
    fun getReminderById(id: Long): ReminderData?

    /*@Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(reminders: List<ReminderData>)*/

    @Query("DELETE FROM reminder_data where id LIKE :id")
    fun deleteById(id: Long?)

    //----Un used now
    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(reminder: ReminderData)

    @Query("DELETE FROM reminder_data")
    fun deleteAll()


}