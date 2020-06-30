package com.example.android.exercisetracker

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.exercisetracker.adapters.WorkoutAdapter
import com.example.android.exercisetracker.models.RoutineWithExercises
import com.example.android.exercisetracker.models.RoutineWithSets
import com.example.android.exercisetracker.viewmodels.RoutineViewModel
import kotlinx.android.synthetic.main.activity_workout.*

class WorkoutActivity : AppCompatActivity() {
    private lateinit var routineViewModel: RoutineViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_workout)

        val routineWithExercises =
            intent.getParcelableExtra<RoutineWithExercises>("routineWithExercises")

        routineTitle.text = routineWithExercises.routine.routineName

        val adapter = WorkoutAdapter(routineWithExercises)
        rv_workoutExercises.adapter = adapter
        rv_workoutExercises.layoutManager = LinearLayoutManager(this)

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        routineViewModel.getRoutineWithSetsById(routineWithExercises.routine.routineId)?.observe(
            this, Observer { routineWithSets ->
                adapter.setRoutineWithSets(routineWithSets)
            }
        )
        adapter.assignTypesToRowItems()
    }
}