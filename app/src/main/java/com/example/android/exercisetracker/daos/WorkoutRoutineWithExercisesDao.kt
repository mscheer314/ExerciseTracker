package com.example.android.exercisetracker.daos

import androidx.room.Query
import androidx.room.Transaction
import com.example.android.exercisetracker.models.WorkoutRoutineWithExercises

interface WorkoutRoutineWithExercisesDao {
    @Transaction
    @Query("SELECT * FROM workoutRoutine_table")
    fun getWorkoutRoutinesAndExercises(): List<WorkoutRoutineWithExercises>
}