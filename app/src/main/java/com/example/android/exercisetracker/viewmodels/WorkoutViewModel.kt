package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class WorkoutViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allWorkouts: LiveData<List<Workout>>

    init {
        repository = AppRepository(application)
        allWorkouts = repository.allWorkouts
    }

    suspend fun insert(workout: Workout): Int {
        var idReturn = 0
        withContext(Dispatchers.IO) {
            val id = async { repository.insert(workout) }
            idReturn = id.await()?.toInt()!!
        }
        return idReturn
    }

    suspend fun delete(workout: Workout) {
        withContext(Dispatchers.IO) {
            repository.delete(workout)
        }
    }

    suspend fun deleteById(id : Int) {
        withContext(Dispatchers.IO) {
            repository.deleteById(id)
        }
    }
}