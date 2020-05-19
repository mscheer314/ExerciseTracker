package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppDatabase
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Exercise
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = AppDatabase.getDatabase(application, viewModelScope).exerciseDao()
        repository = AppRepository(
            exerciseDao
        )
        allExercises = repository.allExercises
    }

    fun insert(exercise: Exercise) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(exercise) }
}