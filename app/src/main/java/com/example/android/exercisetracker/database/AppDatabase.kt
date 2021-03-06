package com.example.android.exercisetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.daos.SetDao
import com.example.android.exercisetracker.daos.WorkoutDao
import com.example.android.exercisetracker.models.*
import com.example.android.exercisetracker.models.Set
import com.example.android.exercisetracker.utils.Converters

@Database(
    entities = [Routine::class, Exercise::class, RoutineExerciseCrossRef::class, Set::class, Workout::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun setDao(): SetDao
    abstract fun workoutDao(): WorkoutDao

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database.db"
                )
                    .createFromAsset("app_database.db")
                    .build()
                INSTANCE = instance
                return instance
            }
        }
    }
}