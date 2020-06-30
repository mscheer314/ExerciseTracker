package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.Routine
import com.example.android.exercisetracker.models.RoutineExerciseCrossRef
import com.example.android.exercisetracker.models.RoutineWithExercises
import com.example.android.exercisetracker.models.RoutineWithSets
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class RoutineViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository = AppRepository(application)
    val allRoutinesWithExercises: LiveData<List<RoutineWithExercises>>

    init {
        allRoutinesWithExercises = repository.allRoutinesWithExercises
    }

    suspend fun insert(routine: Routine): Int? =
        withContext(Dispatchers.IO) { repository.insert(routine) }

    suspend fun insert(routineExerciseCrossRef: RoutineExerciseCrossRef) =
        withContext(Dispatchers.IO) {
            repository.insert(routineExerciseCrossRef)
        }

    fun getRoutineWithSetsById(id: Int): LiveData<RoutineWithSets>? {
        return repository.getRoutineWithSetsById(id)
    }
}