package com.example.android.exercisetracker.database

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.android.exercisetracker.daos.BodyTypeDao
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.ExerciseRoutineJoinDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.models.BodyType
import com.example.android.exercisetracker.models.Exercise
import com.example.android.exercisetracker.models.ExerciseRoutineJoin
import com.example.android.exercisetracker.models.Routine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

class AppRepository(application: Application) : CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    private var exerciseDao: ExerciseDao?
    private var routineDao: RoutineDao?
    private var bodyTypeDao: BodyTypeDao?
    private var exerciseRoutineJoinDao: ExerciseRoutineJoinDao?

    init {
        val database = AppDatabase.getDatabase(application)
        exerciseDao = database.exerciseDao()
        routineDao = database.routineDao()
        bodyTypeDao = database.bodyTypeDao()
        exerciseRoutineJoinDao = database.exerciseRoutineJoinDao()
    }

    val allExercises: LiveData<List<Exercise>> = exerciseDao?.getAllExercises()!!
    val allRoutines: LiveData<List<Routine>> = routineDao?.getRoutines()!!
    val allBodyTypes: LiveData<List<BodyType>> = bodyTypeDao?.getAllBodyTypes()!!
    val allExerciseRoutineJoins: LiveData<List<ExerciseRoutineJoin>> =
        exerciseRoutineJoinDao?.getAllExerciseRoutineJoins()!!

    fun insert(exercise: Exercise) {
        exerciseDao?.insert(exercise)
    }

    suspend fun insert(routine: Routine): Long? =
        routineDao?.insert(routine)


    fun insert(bodyType: BodyType) {
        bodyTypeDao?.insert(bodyType)
    }

    suspend fun insert(exerciseRoutineJoin: ExerciseRoutineJoin) {
        exerciseRoutineJoinDao?.insert(exerciseRoutineJoin)
    }

}