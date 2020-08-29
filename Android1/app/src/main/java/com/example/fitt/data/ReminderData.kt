package com.example.fitt.data

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

    //--------------------------------------------------
    /*This annotation allows you to extend the generation of serialization / deserialization logic for class fields.
    As a result, the writeToParcel () and createFromParcel () methods will be generated automatically.
    Let's look at two examples. An entity class in Kotlin with and without the Parcelize annotation.*/
@Parcelize
data class ReminderData(
        var id: Int = 0,
        var name: String? = null,
        var type: WorkoutType = WorkoutType.Running,
        var hour: Int = 0,
        var minute: Int = 0,
        var days: List<String?>? = null
) : Parcelable