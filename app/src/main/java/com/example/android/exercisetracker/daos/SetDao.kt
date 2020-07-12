package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.Set

@Dao
interface SetDao {
    @Query("SELECT * FROM `set` ORDER BY setId ASC")
    fun getAllSets(): LiveData<List<Set>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(set: Set)
}