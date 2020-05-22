package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "routine_table")
data class Routine(
    var routineName: String,
    var routineExercises: String
) {
    @PrimaryKey(autoGenerate = true)
    var routineId: Int? = null
}