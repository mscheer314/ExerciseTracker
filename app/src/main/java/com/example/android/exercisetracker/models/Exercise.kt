package com.example.android.exercisetracker.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "exercise_table"//,
    /*foreignKeys = [ForeignKey(
        entity = BodyType::class,
        parentColumns = ["body"],
        childColumns = ["bodyTypeId"],
        onDelete = ForeignKey.CASCADE
    )]*/
)
data class Exercise(
    @PrimaryKey(autoGenerate = true)
    val exerciseId: Long,
    val exerciseName: String,
    val routineId: Long?
)