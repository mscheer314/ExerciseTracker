package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.ExerciseRoutineJoin

@Dao
interface ExerciseRoutineJoinDao {

    @Query("SELECT * FROM exercise_routine_join")
    fun getAllExerciseRoutineJoins(): LiveData<List<ExerciseRoutineJoin>>

    // had as .IGNORE - testing another strategy
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(exerciseRoutineJoin: ExerciseRoutineJoin)
}