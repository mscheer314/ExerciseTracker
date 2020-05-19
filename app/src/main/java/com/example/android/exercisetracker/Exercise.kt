package com.example.android.exercisetracker

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "exercise_table")
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int,
    val exerciseName: String,
    val exerciseBodyType: String
)