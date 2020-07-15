package com.example.android.exercisetracker.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import kotlinx.android.parcel.Parcelize
import org.threeten.bp.LocalDate

@Parcelize
@TypeConverters
@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int,
    val date: LocalDate
) : Parcelable {
}