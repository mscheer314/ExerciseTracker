package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = Exercise::class,
        parentColumns = ["exerciseId"],
        childColumns = ["exerciseId"],
        onDelete = CASCADE
    )]
)
data class Set(
    @PrimaryKey(autoGenerate = true)
    val setId: Int,
    val lbs: Int,
    val reps: Int,
    val routineId: Int,
    val exerciseId: Int
)

