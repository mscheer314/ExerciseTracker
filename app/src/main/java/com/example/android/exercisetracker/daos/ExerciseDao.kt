package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.Exercise

@Dao
interface ExerciseDao {
    @Query("SELECT * from exercise ORDER BY exerciseId ASC")
    fun getAllExercises(): LiveData<List<Exercise>>

    @Query("SELECT * FROM exercise WHERE exerciseId = :id")
    fun getExerciseById(id: Int): LiveData<Exercise>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(exercise: Exercise)

    @Query("DELETE FROM exercise")
    fun deleteAll()

}