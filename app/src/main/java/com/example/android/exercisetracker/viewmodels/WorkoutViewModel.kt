package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allWorkouts: LiveData<List<Workout>>

    init {
        repository = AppRepository(application)
        allWorkouts = repository.allWorkouts
    }

    fun insert(workout: Workout) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(workout) }
}