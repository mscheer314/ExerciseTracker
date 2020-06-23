package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.exercisetracker.models.Routine
import com.example.android.exercisetracker.models.RoutineExerciseCrossRef
import com.example.android.exercisetracker.models.RoutineWithExercises

@Dao
interface RoutineDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routineExerciseCrossRef: RoutineExerciseCrossRef)

    @Transaction
    @Query("SELECT * FROM routine WHERE routineId =:id")
    fun getRoutineWithExercises(id: Int): LiveData<List<RoutineWithExercises>>

    @Transaction
    @Query("SELECT * FROM routine")
    fun getRoutinesWithExercises(): LiveData<List<RoutineWithExercises>>
}