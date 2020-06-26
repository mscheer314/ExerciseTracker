package com.example.android.exercisetracker.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int,
    val exerciseName: String
) : Parcelable