package com.example.fitt.utils

import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.widget.AppCompatRadioButton
import com.example.fitt.R
import com.example.fitt.database.entity.ReminderData
import java.text.SimpleDateFormat
import java.util.*

//check PM AM bug
fun hoursAndMinSum(hours: Int, minutes: Int): String {
    val calendar = Calendar.getInstance()
    calendar.set(Calendar.HOUR_OF_DAY, hours)
    calendar.set(Calendar.MINUTE, minutes)
    val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
    return dateFormat.format(calendar.time)
}


/*fun RadioGroup.setupTypeRadioGroup(radioGroup: RadioGroup) {
    val selectedId: Int = radioGroup.checkedRadioButtonId
    val radioButton = findViewById<AppCompatRadioButton>(selectedId)
    radioButton.isChecked = true
}*/

// extension to set up right radio button based on the type
fun setupTypeRadioGroup(reminderData: ReminderData, swimming: AppCompatRadioButton, cycling: AppCompatRadioButton, running: AppCompatRadioButton) {
    when {
        reminderData.type === WorkoutType.Swimming -> {
            swimming.isChecked = true
        }
        reminderData.type === WorkoutType.Cycling -> {
            cycling.isChecked = true
        }
        else -> {
            running.isChecked = true
        }
    }
}

fun LinearLayout.setupDaysCheckBoxes(reminderData: ReminderData, linearLayout: LinearLayout) {
    for (i in 0 until linearLayout.childCount) {
        if (linearLayout.getChildAt(i) is CheckBox) {
            val checkBox = linearLayout.getChildAt(i) as CheckBox
            for (j in reminderData.days!!.indices) {
                if (checkBox.text.toString().equals(
                                reminderData.days!![j],
                                ignoreCase = true
                        )
                ) {
                    checkBox.isChecked = true
                }
            }
        }
    }
}

//function to build all checkboxes
fun LinearLayout.buildCheckBoxes(linearLayoutDatesReceiving: LinearLayout) {
    linearLayoutDatesReceiving.removeAllViews()
    val days = resources.getStringArray(R.array.days)
    for (day in days) {
        val checkBox = CheckBox(context)
        checkBox.text = day
        linearLayoutDatesReceiving.addView(checkBox)
    }
}