package com.example.fitt.fragments

import android.app.AlertDialog
import android.os.Bundle
import android.view.*
import android.widget.CheckBox
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.fitt.FittApp
import com.example.fitt.R
import com.example.fitt.database.WorkoutDatabase
import com.example.fitt.repository.ReminderRepository
import com.example.fitt.utils.*
import com.example.fitt.viewmodel.ReminderViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_third.*
import kotlinx.android.synthetic.main.fragment_third.view.*
import kotlinx.coroutines.runBlocking

class ThirdFragment : Fragment() {

    private val args by navArgs<ThirdFragmentArgs>()

    private lateinit var reminderViewModel: ReminderViewModel
    private val repository: ReminderRepository

    init {
        val userDao = WorkoutDatabase.getDatabase(
                FittApp.applicationContext()
        ).reminderDataDao()
        repository = ReminderRepository(userDao)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)
        reminderViewModel = ViewModelProvider(this).get(ReminderViewModel::class.java)

        //fun to sum time data
        val buttonTimeValue = hoursAndMinSum(args.currentReminder.hour, args.currentReminder.minute)

        //More clear way try to set radio button
        //val radioGroupUpdate = view.findViewById<RadioGroup>(R.id.radioGroupTypeUpdate)
        //radioGroupUpdate.setupTypeRadioGroup(radioGroupUpdate)

        //dirty way
        setupTypeRadioGroup(args.currentReminder, view.swimmingUpdate, view.cyclingUpdate, view.runningUpdate)
        //set name of reminder
        view.textInputWorkoutUpdate.setText(args.currentReminder.name)
        view.buttonTimeUpdate.text = buttonTimeValue

        view.linearLayoutDatesUpdate.buildCheckBoxes(view.linearLayoutDatesUpdate)
        view.linearLayoutDatesUpdate.setupDaysCheckBoxes(args.currentReminder, view.linearLayoutDatesUpdate)

        // Add menu in action bar
        setHasOptionsMenu(true)

        view.fabSaveReminderUpdate.setOnClickListener {
            updateItem()
        }

        return view
    }

    private fun updateItem() {
        if (args.currentReminder.hour == 0 && args.currentReminder.minute == 0 || args.currentReminder.name?.isEmpty() == true) {
            view?.let {
                Snackbar.make(
                        it,
                        "Заполните все поля",
                        Snackbar.LENGTH_LONG
                ).show()
            }
        } else {
            // Gather all the fields
            val name = textInputWorkoutUpdate!!.text!!.toString()
            val checkedId = radioGroupTypeUpdate!!.checkedRadioButtonId
            val dateType: WorkoutType

            //check type of current training
            dateType = when (checkedId) {
                R.id.swimmingUpdate -> {
                    WorkoutType.Swimming
                }
                R.id.cyclingUpdate -> {
                    WorkoutType.Cycling
                }
                else -> {
                    WorkoutType.Running
                }
            }
            //data structure for selected days in week
            val daysItems = resources.getStringArray(R.array.days).toMutableList()

            //check selected checkbox of dates
            for (i in 0 until linearLayoutDatesUpdate!!.childCount) {
                if (linearLayoutDatesUpdate!!.getChildAt(i) is CheckBox) {
                    val checkBox = linearLayoutDatesUpdate!!.getChildAt(i) as CheckBox
                    if (!checkBox.isChecked) {
                        daysItems[i] = null
                    }
                }
            }

            //create reminder and save to database
            //create object pf reminder
            val id = createUpdatedReminder(name = name, dateType = dateType, days = daysItems.filter { !it.isNullOrEmpty() }.toList())
            val reminder = repository.getReminderById(id)
            //update reminder
            reminderViewModel.updateReminder(reminder)
            Toast.makeText(requireContext(), "Updated Successfully!", Toast.LENGTH_SHORT).show()
            // Navigate Back
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.delete_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menu_delete) {
            deleteUser()
        }
        return super.onOptionsItemSelected(item)
    }

    //refactor to extension fun
    private fun createUpdatedReminder(name: String, dateType: WorkoutType, days: List<String?>?): Long {
        args.currentReminder.name = name
        args.currentReminder.type = dateType
        args.currentReminder.days = days

        fun idReturn(): Long = runBlocking {
            repository.insertReminderRepository(args.currentReminder)
        }
        return idReturn()
    }

    private fun deleteUser() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yes") { _, _ ->
            reminderViewModel.deleteReminder(args.currentReminder)
            Toast.makeText(
                    requireContext(),
                    "Successfully removed: ${args.currentReminder.name}",
                    Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("No") { _, _ -> }
        builder.setTitle("Delete ${args.currentReminder.name}?")
        builder.setMessage("Are you sure you want to delete ${args.currentReminder.name}?")
        builder.create().show()
    }

}