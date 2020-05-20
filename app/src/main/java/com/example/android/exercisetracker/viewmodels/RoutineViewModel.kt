package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Routine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allRoutines: LiveData<List<Routine>>

    init {
        repository = AppRepository(application)
        allRoutines = repository.allRoutines
    }

    fun insert(routine: Routine) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(routine) }
}