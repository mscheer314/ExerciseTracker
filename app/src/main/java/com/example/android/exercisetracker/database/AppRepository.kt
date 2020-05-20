package com.example.android.exercisetracker.database

import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.models.Exercise

class AppRepository(private val exerciseDao: ExerciseDao) {
    val allExercises: LiveData<List<Exercise>> = exerciseDao.getAllExercises()

    suspend fun insert(exercise: Exercise) {
        exerciseDao.insert(exercise)
    }
}