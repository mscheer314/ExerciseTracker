package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.Exercise

@Dao
interface ExerciseDao {
    @Query("SELECT * from exercise_table ORDER BY exerciseId ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(exercise: Exercise)

    @Query("DELETE FROM exercise_table")
    fun deleteAll()

    // @Query("SELECT exerciseId, exerciseName, ExerciseInRoutine.routineId, Routine.*")
}