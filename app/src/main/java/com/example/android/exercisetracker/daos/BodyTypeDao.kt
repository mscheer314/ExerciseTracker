package com.example.android.exercisetracker.daos

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.android.exercisetracker.models.BodyType

@Dao
interface BodyTypeDao {
    @Query("SELECT * FROM body_type_table ORDER BY bodyTypeId ASC")
    fun getAllBodyTypes(): LiveData<List<BodyType>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(bodyType: BodyType)
}