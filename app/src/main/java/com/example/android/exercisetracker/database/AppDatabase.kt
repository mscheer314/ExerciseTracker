package com.example.android.exercisetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.models.Exercise
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(entities = arrayOf(Exercise::class), version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { appDatabase ->
                scope.launch {
                    val exerciseDao = appDatabase.exerciseDao()
                    exerciseDao.deleteAll()

                    //sample exercises
                    var exercise =
                        Exercise(
                            10,
                            "Burpees",
                            "Full Body",
                            null
                        )
                    exerciseDao.insert(exercise)
                    exercise =
                        Exercise(
                            11,
                            "Dive Bombers",
                            "Shoulders",
                            null
                        )
                    exerciseDao.insert(exercise)
                }
            }
        }
    }

    companion object {
        // Singleton prevents multiple instances of database opening at the
        // same time.
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(
            context: Context, scope: CoroutineScope
        ): AppDatabase {
            return INSTANCE
                ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_database"
                )
                    .addCallback(
                        AppDatabaseCallback(
                            scope
                        )
                    )
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}