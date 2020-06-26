package com.example.android.exercisetracker.models

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val routineId: Int,
    val routineName: String
) : Parcelable