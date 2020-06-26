package com.example.android.exercisetracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.android.exercisetracker.models.RoutineWithExercises

class WorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val routineWithExercises = intent.getParcelableExtra<RoutineWithExercises>("routine")
        Log.i("routineW-E Name", routineWithExercises.routine.routineName)
    }
}