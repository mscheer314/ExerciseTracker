package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Routine
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allRoutines: LiveData<List<Routine>>

    init {
        repository = AppRepository(application)
        allRoutines = repository.allRoutines
    }

    suspend fun insert(routine: Routine): Long? =
        withContext(Dispatchers.IO) { repository.insert(routine) }

}