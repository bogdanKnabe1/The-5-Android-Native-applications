package com.example.fitt.repository

import android.os.Parcelable
import androidx.room.*
import kotlinx.android.parcel.Parcelize

//--------------------------------------------------
/*This annotation allows you to extend the generation of serialization / deserialization logic for class fields.
As a result, the writeToParcel () and createFromParcel () methods will be generated automatically.
Let's look at two examples. An entity class in Kotlin with and without the Parcelize annotation.*/

@Entity(tableName = "reminder_data")
@Parcelize
data class ReminderData(
        @PrimaryKey(autoGenerate = true)
        var id: Int = 0,
        var name: String? = null,
        var type: WorkoutType = WorkoutType.Running,
        var hour: Int = 0,
        var minute: Int = 0,
        var days: List<String?>? = null
) : Parcelable

@Dao
interface ReminderDao {

    @Query("SELECT * from reminder_data")
    fun getReminderData(): List<ReminderData>

    @Query("SELECT * from reminder_data where id= :id")
    fun getReminderById(id: Long): ReminderData?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: ReminderData): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<ReminderData>)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun update(reminder: ReminderData)

    @Query("DELETE FROM reminder_data")
    fun deleteAll()

    @Query("DELETE FROM reminder_data where id LIKE :id")
    fun deleteById(id: Long)
}