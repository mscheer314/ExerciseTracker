package com.example.android.exercisetracker.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.models.*
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
        exerciseDao = database.exerciseDao()
        routineDao = database.routineDao()
    }

    val allExercises: LiveData<List<Exercise>> = exerciseDao?.getAllExercises()!!
    val allRoutinesWithExercises: LiveData<List<RoutineWithExercises>> =
        routineDao?.getRoutinesWithExercises()!!

    fun insert(exercise: Exercise) {
        exerciseDao?.insert(exercise)
    }

    suspend fun insert(routine: Routine): Int? =
        routineDao?.insert(routine)?.toInt()

    suspend fun insert(routineExerciseCrossRef: RoutineExerciseCrossRef) {
        routineDao?.insert(routineExerciseCrossRef)
    }

    fun getExerciseById(id: Int): LiveData<Exercise>? {
        return exerciseDao?.getExerciseById(id)
    }

    fun getRoutineWithSetsById(id: Int): LiveData<RoutineWithSets>? {
        return routineDao?.getRoutineWithSets(id)
    }

}