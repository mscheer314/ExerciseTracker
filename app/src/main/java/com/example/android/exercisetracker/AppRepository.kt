package com.example.android.exercisetracker

import androidx.lifecycle.LiveData

class AppRepository(private val exerciseDao: ExerciseDao) {
    val allExercises: LiveData<List<Exercise>> = exerciseDao.getAllExcercises()

    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }
}