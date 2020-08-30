package com.example.fitt.utils

import androidx.room.TypeConverter
import com.example.fitt.repository.WorkoutType

class WorkoutTypeConverter {
    @TypeConverter
    fun toStatus(numeral: Int): WorkoutType {
        return when (numeral) {
            0 -> WorkoutType.Swimming
            1 -> WorkoutType.Cycling
            2 -> WorkoutType.Running
            else -> WorkoutType.Swimming
        }
    }

    @TypeConverter
    fun fromStatus(type: WorkoutType): Int {
        return when (type) {
            WorkoutType.Swimming -> 0
            WorkoutType.Cycling -> 1
            WorkoutType.Running -> 2
        }
    }
}