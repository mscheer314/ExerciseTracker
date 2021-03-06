package com.example.android.exercisetracker.models

import androidx.room.Entity

@Entity(primaryKeys = ["routineId", "exerciseId"])
data class RoutineExerciseCrossRef(
    val routineId: Int,
    val exerciseId: Int
)
