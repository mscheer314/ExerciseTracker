package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.exercisetracker.models.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout ORDER BY workoutId ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(workout: Workout): Long

    @Delete
    suspend fun delete(workout: Workout)

    @Query("DELETE FROM workout WHERE workoutId = :id ")
    suspend fun deleteById(id: Int)
}