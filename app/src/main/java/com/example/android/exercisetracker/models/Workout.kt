package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*

@Entity
data class Workout(
    @PrimaryKey(autoGenerate = true)
    val workoutId: Int,
    val date: Date
) {
}