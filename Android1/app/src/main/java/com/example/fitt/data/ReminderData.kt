package com.example.fitt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class ReminderData(
        var id: Int = 0,
        var name: String? = null,
        var type: WorkoutType = WorkoutType.Swimming,
        var hour: Int = 0,
        var minute: Int = 0,
        var days: List<String?>? = null
) : Parcelable