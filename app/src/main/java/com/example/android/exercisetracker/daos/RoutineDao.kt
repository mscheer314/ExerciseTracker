package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.android.exercisetracker.models.Routine

@Dao
interface RoutineDao {
    @Transaction
    @Query("SELECT * FROM routine_table")
    fun getRoutines(): LiveData<List<Routine>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(routine: Routine): Long
}