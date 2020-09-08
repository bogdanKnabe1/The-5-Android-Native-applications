package com.example.fitt.database.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.fitt.utils.WorkoutType
import kotlinx.android.parcel.Parcelize

//--------------------------------------------------
/*This annotation allows you to extend the generation of serialization / deserialization logic for class fields.
As a result, the writeToParcel () and createFromParcel () methods will be generated automatically.
Let's look at two examples. An entity class in Kotlin with and without the Parcelize annotation.*/

@Entity(tableName = "reminder_data")
@Parcelize
data class ReminderData(
        @PrimaryKey(autoGenerate = true)
        var id: Long = 0,
        var name: String? = null,
        var type: WorkoutType = WorkoutType.Running,
        var hour: Int = 0,
        var minute: Int = 0,
        var days: List<String?>? = null,
        var state: Boolean = false
) : Parcelable
