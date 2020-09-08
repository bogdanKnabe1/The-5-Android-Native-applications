package com.example.fitt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.fitt.R
import com.example.fitt.database.entity.ReminderData
import com.example.fitt.fragments.FirstFragmentDirections
import com.example.fitt.utils.WorkoutType
import kotlinx.android.synthetic.main.layout_reminder_row.view.*
import java.text.SimpleDateFormat
import java.util.*

//Adapter for main list of tasks
class MainAdapter: RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var reminderDataList = emptyList<ReminderData>()

    private val dateFormat = SimpleDateFormat("h:mma", Locale.getDefault())


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_reminder_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        //getting data from list, i = position
        val reminderData = reminderDataList[position]
        //set name
        viewHolder.textViewName.text = reminderData.name
        //get data
        val date = Calendar.getInstance()
        date.set(Calendar.HOUR_OF_DAY, reminderData.hour)
        date.set(Calendar.MINUTE, reminderData.minute)
        //set time
        viewHolder.textViewTimeToAdminister.text = dateFormat.format(date.time).toLowerCase(Locale.ROOT)
        //?
        var daysText = reminderData.days.toString()
        daysText = daysText.replace("[", "")
        daysText = daysText.replace("]", "")
        daysText = daysText.replace(",", " ·")
        //set day
        viewHolder.textViewDays.text = daysText
        //find drawable
        val drawable = when (reminderData.type) {
            WorkoutType.Swimming -> ContextCompat.getDrawable(
                    viewHolder.imageViewIcon.context,
                    R.drawable.ic_swimming
            )
            WorkoutType.Cycling -> ContextCompat.getDrawable(
                    viewHolder.imageViewIcon.context,
                    R.drawable.ic_bicycle
            )
            else -> ContextCompat.getDrawable(
                    viewHolder.imageViewIcon.context,
                    R.drawable.ic_run
            )
        }
        //set drawable
        viewHolder.imageViewIcon.setImageDrawable(drawable)

        //direction from row in recycler with data of this row INTO update fragment.
        viewHolder.itemView.rowReminderLayout.setOnClickListener {
            val action = FirstFragmentDirections.actionFirstFragmentToThirdFragment(reminderData)
            viewHolder.itemView.findNavController().navigate(action)
        }

    }

    override fun getItemCount(): Int {
        return reminderDataList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //find all view's
        var imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)
        var textViewName: TextView = itemView.findViewById(R.id.textViewName)
        var textViewTimeToAdminister: TextView = itemView.findViewById(R.id.textViewTimeToAdminister)
        var textViewDays: TextView = itemView.findViewById(R.id.textViewDays)
        var checkBoxAdministered: CheckBox = itemView.findViewById(R.id.checkBoxAdministered)
    }

    fun setData(reminderData: List<ReminderData>) {
        this.reminderDataList = reminderData
        notifyDataSetChanged()
    }
}