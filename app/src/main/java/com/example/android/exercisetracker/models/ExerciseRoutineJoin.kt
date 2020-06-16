package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    tableName = "exercise_routine_join",
    primaryKeys = arrayOf("exerciseId", "routineId"),
    foreignKeys = arrayOf(
        ForeignKey(
            entity = Exercise::class,
            parentColumns = arrayOf("exerciseId"),
            childColumns = arrayOf("exerciseId"),
            onDelete = ForeignKey.NO_ACTION
        ),
        ForeignKey(
            entity = Routine::class,
            parentColumns = arrayOf("routineId"),
            childColumns = arrayOf("routineId"),
            onDelete = ForeignKey.NO_ACTION
        )
    )
)
data class ExerciseRoutineJoin(val exerciseId: Long, val routineId: Long)