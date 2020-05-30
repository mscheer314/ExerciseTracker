package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Relation

class ExerciseAllSets {
    @Embedded
    var exercise: Exercise? = null

    @Relation(parentColumn = "exerciseId", entityColumn = "exerciseId")
    var sets: List<Set>? = null
}