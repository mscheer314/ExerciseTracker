package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.Workout

@Dao
interface WorkoutDao {
    @Query("SELECT * FROM workout ORDER BY workoutId ASC")
    fun getAllWorkouts(): LiveData<List<Workout>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(workout: Workout)
}