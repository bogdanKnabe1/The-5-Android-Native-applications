package com.example.fitt.fragments

import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.fitt.R
import com.example.fitt.notification.AlarmScheduler
import com.example.fitt.repository.ReminderData
import com.example.fitt.repository.ReminderLocalRepository
import com.example.fitt.repository.WorkoutType
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_second.*
import java.text.SimpleDateFormat
import java.util.*

const val KEY_ID = "id"

class SecondFragment : Fragment() {

    private var reminderData = ReminderData()

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val actionBar = (activity as AppCompatActivity).supportActionBar
        actionBar?.title = view.context.getString(R.string.fragment_title)
        buildCheckBoxes(linearLayoutDates)
        buttonTime.setOnClickListener {
            timeTapped()
        }

        //check all fields and save all reminds into database (ROOM)
        fabSaveReminder.setOnClickListener {
            if (reminderData.hour == 0 && reminderData.minute == 0) {
                Snackbar.make(
                        view,
                        "Вы не выбрали время",
                        Snackbar.LENGTH_LONG
                ).show()
            } else {
                // Gather all the fields
                val name = textInputWorkout!!.text!!.toString()
                val checkedId = radioGroupType!!.checkedRadioButtonId
                val dateType: WorkoutType

                //check type of current training
                dateType = when (checkedId) {
                    R.id.swimming -> {
                        WorkoutType.Swimming
                    }
                    R.id.cycling -> {
                        WorkoutType.Cycling
                    }
                    else -> {
                        WorkoutType.Running
                    }
                }
                //data structure for selected days in week
                val daysItems = resources.getStringArray(R.array.days).toMutableList()

                //check selected checkbox of dates
                for (i in 0 until linearLayoutDates!!.childCount) {
                    if (linearLayoutDates!!.getChildAt(i) is CheckBox) {
                        val checkBox = linearLayoutDates!!.getChildAt(i) as CheckBox
                        if (!checkBox.isChecked) {
                            daysItems[i] = null
                        }
                    }
                }
                //create reminder and save to database
                val id = createReminder(
                        name = name,
                        dateType = dateType,
                        days = daysItems.filter { !it.isNullOrEmpty() }.toList()
                )

                val reminder =
                        ReminderLocalRepository(activity?.applicationContext).getReminderById(id)

                AlarmScheduler.scheduleAlarmsForReminder(activity?.applicationContext!!, reminder!!)
                Snackbar.make(
                        view,
                        "Напоминание о тренировке создано!",
                        Snackbar.LENGTH_LONG
                ).show()
            }
        }
    }
    //set text with current data to timeButton
    private fun setTimeButtonText(hourOfDay: Int, minute: Int) {
        val calendar = Calendar.getInstance()
        calendar.set(Calendar.HOUR_OF_DAY, hourOfDay)
        calendar.set(Calendar.MINUTE, minute)
        val dateFormat = SimpleDateFormat("h:mm a", Locale.getDefault())
        buttonTime?.text = dateFormat.format(calendar.time)
    }

    //function which one is invoked when we click on Time selection
    private fun timeTapped() {
        reminderData = ReminderData()
        if (reminderData.id != 0) {
            displayTimeDialog(reminderData.hour, reminderData.minute)
        } else {
            val date = Calendar.getInstance()
            val hour = date.get(Calendar.HOUR_OF_DAY)
            val minute = date.get(Calendar.MINUTE)
            displayTimeDialog(hour, minute)
        }
    }

    fun displayExistingReminder(reminderData: ReminderData) {
        textInputWorkout!!.setText(reminderData.name)
        setupTypeRadioGroup(reminderData)
        setTimeButtonText(reminderData.hour, reminderData.minute)
        setupDaysCheckBoxes(reminderData)
    }

    //Time picker dialog
    private fun displayTimeDialog(hour: Int, minute: Int) {
        val timePickerDialog = TimePickerDialog(
                context,
                { view, hourOfDay, minute ->
                    setHourAndMinute(hourOfDay, minute)
                    setTimeButtonText(hourOfDay, minute)
                },
                hour,
                minute,
                false
        )
        timePickerDialog.show()
    }

    private fun setHourAndMinute(hourOfDay: Int, minute: Int) {
        reminderData = ReminderData()
        reminderData.hour = hourOfDay
        reminderData.minute = minute
    }

    //check type of the current Training
    private fun setupTypeRadioGroup(reminderData: ReminderData) {
        when {
            reminderData.type === WorkoutType.Swimming -> {
                swimming!!.isChecked = true
            }
            reminderData.type === WorkoutType.Cycling -> {
                cycling!!.isChecked = true
            }
            else -> {
                running!!.isChecked = true
            }
        }
    }

    private fun setupDaysCheckBoxes(reminderData: ReminderData) {
        for (i in 0 until linearLayoutDates!!.childCount) {
            if (linearLayoutDates!!.getChildAt(i) is CheckBox) {
                val checkBox = linearLayoutDates!!.getChildAt(i) as CheckBox
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

    //create obj Reminder
    fun createReminder(name: String, dateType: WorkoutType, days: List<String?>?): Long {
        reminderData.name = name
        reminderData.type = dateType
        reminderData.days = days

        return ReminderLocalRepository(activity?.applicationContext).saveReminder(reminderData)
    }
    //function to build all checkboxes
    private fun buildCheckBoxes(linearLayoutDates: LinearLayout) {
        linearLayoutDates.removeAllViews()
        val days = resources.getStringArray(R.array.days)
        for (day in days) {
            val checkBox = CheckBox(context)
            checkBox.text = day
            linearLayoutDates.addView(checkBox)
        }
    }
}