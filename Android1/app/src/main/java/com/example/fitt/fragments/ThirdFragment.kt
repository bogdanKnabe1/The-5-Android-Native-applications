package com.example.fitt.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.example.fitt.R
import com.example.fitt.utils.buildCheckBoxes
import com.example.fitt.utils.hoursAndMinSum
import com.example.fitt.utils.setupDaysCheckBoxes
import com.example.fitt.utils.setupTypeRadioGroup
import com.example.fitt.viewmodel.ReminderViewModel
import kotlinx.android.synthetic.main.fragment_third.view.*

class ThirdFragment : Fragment() {

    private val args by navArgs<ThirdFragmentArgs>()

    private lateinit var reminderViewModel: ReminderViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_third, container, false)
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


        return view
    }

}