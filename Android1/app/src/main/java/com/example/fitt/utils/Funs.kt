package com.example.fitt.utils

import java.text.SimpleDateFormat
import java.util.*

//check PM AM bug
fun hoursAndMinSum(hours: Int, minutes: Int): String{
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)
}