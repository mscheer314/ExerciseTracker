package com.example.android.exercisetracker

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allExercises: LiveData<List<Exercise>>

    init {
        val exerciseDao = AppDatabase.getDatabase(application, viewModelScope).exerciseDao()
        repository = AppRepository(exerciseDao)
        allExercises = repository.allExercises
    }

    fun insert(exercise: Exercise) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(exercise) }
}