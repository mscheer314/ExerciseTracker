package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Int,
    val exerciseName: String
)