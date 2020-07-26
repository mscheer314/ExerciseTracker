package com.example.android.exercisetracker.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.daos.SetDao
import com.example.android.exercisetracker.daos.WorkoutDao
import com.example.android.exercisetracker.models.*
import com.example.android.exercisetracker.models.Set
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class AppRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var exerciseDao: ExerciseDao?
    private var routineDao: RoutineDao?
    private var setDao: SetDao?
    private var workoutDao: WorkoutDao?

    init {
        val database = AppDatabase.getDatabase(application)
        exerciseDao = database.exerciseDao()
        routineDao = database.routineDao()
        setDao = database.setDao()
        workoutDao = database.workoutDao()
    }

    val allExercises: LiveData<List<Exercise>> = exerciseDao?.getAllExercises()!!
    val allRoutinesWithExercises: LiveData<List<RoutineWithExercises>> =
        routineDao?.getRoutinesWithExercises()!!
    val allSets: LiveData<List<Set>> = setDao?.getAllSets()!!
    val allWorkouts: LiveData<List<Workout>> = workoutDao?.getAllWorkouts()!!

    fun insert(exercise: Exercise) {
        exerciseDao?.insert(exercise)
    }

    suspend fun insert(routine: Routine): Int? =
        routineDao?.insert(routine)?.toInt()

    suspend fun insert(routineExerciseCrossRef: RoutineExerciseCrossRef) {
        routineDao?.insert(routineExerciseCrossRef)
    }

    fun insert(set: Set) {
        setDao?.insert(set)
    }

    suspend fun insert(workout: Workout) : Long? {
       return  workoutDao?.insert(workout)
    }

    fun getExerciseById(id: Int): LiveData<Exercise>? {
        return exerciseDao?.getExerciseById(id)
    }

    fun getRoutineWithSetsById(id: Int): LiveData<RoutineWithSets>? {
        return routineDao?.getRoutineWithSets(id)
    }

    suspend fun delete(workout: Workout) {
        workoutDao?.delete(workout)
    }

    suspend fun deleteById(id: Int) {
        workoutDao?.deleteById(id)
    }

}