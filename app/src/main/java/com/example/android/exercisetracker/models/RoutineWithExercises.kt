package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class RoutineWithExercises(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "exerciseId",
        associateBy = Junction(RoutineExerciseCrossRef::class)
    )
    val exercices: List<Exercise>
)