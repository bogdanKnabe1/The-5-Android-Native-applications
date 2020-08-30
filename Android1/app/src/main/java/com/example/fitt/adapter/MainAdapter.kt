package com.example.fitt.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.fitt.R
import com.example.fitt.repository.ReminderData
import com.example.fitt.repository.WorkoutType
import java.text.SimpleDateFormat
import java.util.*

//Adapter for main list of tasks
class MainAdapter(
        private val listener: OnClickReminderListener,
        private val reminderDataList: List<ReminderData>?
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("h:mma", Locale.getDefault())

    interface OnClickReminderListener {
        fun onClick(reminderData: ReminderData)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
                .inflate(R.layout.layout_reminder_row, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        if (reminderDataList != null) {
            //getting data from list, i = position
            val reminderData = reminderDataList[i]
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

            //pass data to fragment with onClick
            viewHolder.itemView.setOnClickListener {
                listener.onClick(reminderData)
            }

        }
    }

    override fun getItemCount(): Int {
        return reminderDataList?.size ?: 0
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        //find all view's
        var imageViewIcon: ImageView = itemView.findViewById(R.id.imageViewIcon)
        var textViewName: TextView = itemView.findViewById(R.id.textViewName)
        var textViewTimeToAdminister: TextView = itemView.findViewById(R.id.textViewTimeToAdminister)
        var textViewDays: TextView = itemView.findViewById(R.id.textViewDays)
        var checkBoxAdministered: CheckBox = itemView.findViewById(R.id.checkBoxAdministered)

    }
}