package com.example.fitt.utils

import androidx.room.TypeConverter

const val SEPARATOR = ";"

class DaysConverter {
    @TypeConverter
    fun toDaysString(days: List<String>): String {
        return days.joinToString(SEPARATOR)
    }

    @TypeConverter
    fun fromDaysString(days: String): List<String> {
        return days.split(SEPARATOR).toList()
    }
}