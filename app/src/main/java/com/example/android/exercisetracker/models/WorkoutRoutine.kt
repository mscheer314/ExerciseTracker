package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workoutRoutine_table")
data class WorkoutRoutine(
    @PrimaryKey(autoGenerate = true)
    val workoutRoutineId: Int,
    val workoutRoutineName: String
)