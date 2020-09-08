package com.example.fitt.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.fitt.database.dao.ReminderDao
import com.example.fitt.database.entity.ReminderData
import com.example.fitt.utils.DaysConverter
import com.example.fitt.utils.WorkoutTypeConverter

//database creation + version
@Database(entities = [ReminderData::class], version = 2)
//links to converters
@TypeConverters(WorkoutTypeConverter::class, DaysConverter::class)
/* To save training types (Running / Swimming / Cycling), you need to write the corresponding data converter
In addition, we need to save a list of days for which the training will take place - accordingly,
a converter from a list of lines to a line is needed - this is the easiest way to save the list.*/
abstract class WorkoutDatabase : RoomDatabase() {

    abstract fun reminderDataDao(): ReminderDao

}