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
import com.example.fitt.data.ReminderData
import com.example.fitt.data.WorkoutType
import java.text.SimpleDateFormat
import java.util.*

//Adapter for main list of tasks
class MainAdapter(
        private val listener: OnClickReminderListener,
        private val reminderDataList: List<ReminderData>?
) : RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private val dateFormat = SimpleDateFormat("h:mma", Locale.getDefault());

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
            val reminderData = reminderDataList[i]

            viewHolder.textViewName.text = reminderData.name
            val date = Calendar.getInstance()
            date.set(Calendar.HOUR_OF_DAY, reminderData.hour)
            date.set(Calendar.MINUTE, reminderData.minute)
            viewHolder.textViewTimeToAdminister.text = dateFormat.format(date.time).toLowerCase()

            var daysText = reminderData.days.toString()
            daysText = daysText.replace("[", "")
            daysText = daysText.replace("]", "")
            daysText = daysText.replace(",", " ·")
            viewHolder.textViewDays.text = daysText

            val drawable = when {
                reminderData.type == WorkoutType.Swimming -> ContextCompat.getDrawable(
                        viewHolder.imageViewIcon.context,
                        R.drawable.ic_swimming
                )
                reminderData.type == WorkoutType.Cycling -> ContextCompat.getDrawable(
                        viewHolder.imageViewIcon.context,
                        R.drawable.ic_bicycle
                )
                else -> ContextCompat.getDrawable(
                        viewHolder.imageViewIcon.context,
                        R.drawable.ic_run
                )
            }
            viewHolder.imageViewIcon.setImageDrawable(drawable)

            viewHolder.itemView.setOnClickListener {
                listener.onClick(reminderData)
            }

        }
    }

    override fun getItemCount(): Int {
        return reminderDataList?.size ?: 0
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var imageViewIcon: ImageView
        var textViewName: TextView
        var textViewTimeToAdminister: TextView
        var textViewDays: TextView
        var checkBoxAdministered: CheckBox

        init {

            imageViewIcon = itemView.findViewById(R.id.imageViewIcon)
            textViewName = itemView.findViewById(R.id.textViewName)
            textViewTimeToAdminister = itemView.findViewById(R.id.textViewTimeToAdminister)
            textViewDays = itemView.findViewById(R.id.textViewDays)
            checkBoxAdministered = itemView.findViewById(R.id.checkBoxAdministered)
        }
    }
}