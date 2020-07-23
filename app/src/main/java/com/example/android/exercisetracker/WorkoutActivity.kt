package com.example.android.exercisetracker

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.android.exercisetracker.adapters.WorkoutAdapter
import com.example.android.exercisetracker.models.RoutineWithExercises
import com.example.android.exercisetracker.models.Workout
import com.example.android.exercisetracker.viewmodels.RoutineViewModel
import com.example.android.exercisetracker.viewmodels.SetViewModel
import com.example.android.exercisetracker.viewmodels.WorkoutViewModel
import kotlinx.android.synthetic.main.activity_workout.*
import kotlinx.coroutines.runBlocking
import org.threeten.bp.LocalDate

class WorkoutActivity : AppCompatActivity() {
    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var workoutViewModel: WorkoutViewModel

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

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)

        runBlocking {
            val workoutId = workoutViewModel.insert(Workout(0, LocalDate.now()))
            adapter.setWorkoutId(workoutId)
            adapter.assignTypesToRowItems()
        }

        val finishedButton = findViewById<Button>(R.id.finishedButton)
        finishedButton.setOnClickListener {
            val setViewModel = ViewModelProvider(this).get(SetViewModel::class.java)
            val sets = adapter.getWorkoutSets()
            sets.forEach { set ->
                setViewModel.insert(set)
            }
            val intent = Intent(this, PrepWorkoutActivity::class.java)
            startActivity(intent)
        }
    }
}

