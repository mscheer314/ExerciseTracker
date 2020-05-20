package com.example.android.exercisetracker.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.models.Exercise
import com.example.android.exercisetracker.models.Routine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class AppRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var exerciseDao: ExerciseDao?
    private var routineDao: RoutineDao?

    init {
        val database = AppDatabase.getDatabase(application)
        exerciseDao = database?.exerciseDao()
        routineDao = database?.routineDao()
    }

    val allExercises: LiveData<List<Exercise>> = exerciseDao?.getAllExercises()!!
    val allRoutines: LiveData<List<Routine>> = routineDao?.getRoutines()!!

    suspend fun insert(exercise: Exercise) {
        exerciseDao?.insert(exercise)
    }

    suspend fun insert(routine: Routine) {
        routineDao?.insert(routine)
    }
}