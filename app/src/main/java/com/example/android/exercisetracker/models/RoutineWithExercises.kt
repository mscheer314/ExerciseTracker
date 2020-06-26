package com.example.android.exercisetracker.models

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation
import kotlinx.android.parcel.Parcelize

@Parcelize
data class RoutineWithExercises(
    @Embedded val routine: Routine,
    @Relation(
        parentColumn = "routineId",
        entityColumn = "exerciseId",
        associateBy = Junction(RoutineExerciseCrossRef::class)
    )
    val exercises: List<Exercise>
) : Parcelable