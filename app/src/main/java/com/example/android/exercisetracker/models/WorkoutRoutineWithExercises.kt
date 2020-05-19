package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Relation

data class WorkoutRoutineWithExercises (
    @Embedded
    val workoutRoutine: WorkoutRoutine,
    @Relation (
        parentColumn = "workoutRoutineId",
        entityColumn = "routineId"
    )
    val exercises: List<Exercise>
)