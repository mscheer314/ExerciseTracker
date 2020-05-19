package com.example.android.exercisetracker

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ExerciseDao {
    @Query("SELECT * from exercise_table ORDER BY exerciseId ASC")
    fun getAllExcercises(): LiveData<List<Exercise>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(exercise: Exercise)

    @Query("DELETE FROM exercise_table")
    suspend fun deleteAll()
}