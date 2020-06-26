package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Relation

data class RoutineWithSets(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "routineId"
    )
    val sets: List<Set>
)