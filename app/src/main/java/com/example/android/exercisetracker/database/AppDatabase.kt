package com.example.android.exercisetracker.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.android.exercisetracker.daos.BodyTypeDao
import com.example.android.exercisetracker.daos.ExerciseDao
import com.example.android.exercisetracker.daos.RoutineDao
import com.example.android.exercisetracker.models.*
import com.example.android.exercisetracker.models.Set
import com.example.android.exercisetracker.utils.Converters
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Database(
    entities = arrayOf(
        Workout::class,
        Routine::class,
        Exercise::class,
        Set::class,
        BodyType::class
    ),
    version = 1,
    exportSchema = false
)
@TypeConverters(Converters::class)

abstract class AppDatabase : RoomDatabase() {

    abstract fun exerciseDao(): ExerciseDao
    abstract fun routineDao(): RoutineDao
    abstract fun bodyTypeDao(): BodyTypeDao

    private class AppDatabaseCallback(
        private val scope: CoroutineScope
    ) : RoomDatabase.Callback() {
        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            INSTANCE?.let { appDatabase ->
                scope.launch {
                    val exerciseDao = appDatabase.exerciseDao()
                    val bodyTypeDao = appDatabase.bodyTypeDao()
                }
            }
        }
    }

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
                    "app_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}