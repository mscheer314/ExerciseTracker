package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "routine_table")
data class Routine(
    @PrimaryKey(autoGenerate = true)
    val routineId: Long,
    val routineName: String
)