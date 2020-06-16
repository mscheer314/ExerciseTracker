package com.example.android.exercisetracker.viewmodels

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.example.android.exercisetracker.database.AppRepository
import com.example.android.exercisetracker.models.ExerciseRoutineJoin
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExerciseRoutineJoinViewModel(application: Application) : AndroidViewModel(application) {
    private val repository: AppRepository
    val allExerciseRoutineJoins: LiveData<List<ExerciseRoutineJoin>>

    init {
        repository = AppRepository(application)
        allExerciseRoutineJoins = repository.allExerciseRoutineJoins
    }

    fun insert(exerciseRoutineJoin: ExerciseRoutineJoin) =
        viewModelScope.launch(Dispatchers.IO) { repository.insert(exerciseRoutineJoin) }
}