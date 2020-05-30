package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Relation

class RoutineAllSets {
    @Embedded
    var routine: Routine? = null

    @Relation(parentColumn = "setId", entityColumn = "setId")
    var sets: List<Set>? = null
}