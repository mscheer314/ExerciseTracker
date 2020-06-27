package com.example.android.exercisetracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.exercisetracker.adapters.WorkoutAdapter
import com.example.android.exercisetracker.models.RoutineWithExercises
import kotlinx.android.synthetic.main.activity_workout.*

class WorkoutActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val routineWithExercises =
            intent.getParcelableExtra<RoutineWithExercises>("routineWithExercises")
        Log.i("routineW-E Name", routineWithExercises.routine.routineName)

        rv_workoutExercises.adapter = WorkoutAdapter(routineWithExercises)
        rv_workoutExercises.layoutManager = LinearLayoutManager(this)
    }
}