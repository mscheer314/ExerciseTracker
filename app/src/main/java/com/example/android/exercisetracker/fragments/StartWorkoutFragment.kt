package com.example.android.exercisetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.adapters.RoutineAdapter
import com.example.android.exercisetracker.viewmodels.ExerciseViewModel
import com.example.android.exercisetracker.viewmodels.RoutineViewModel

class StartWorkoutFragment : Fragment() {
    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var exerciseViewModel: ExerciseViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_workout, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_routines)
        val adapter = RoutineAdapter(activity)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

        setUpRecyclerViewContent(adapter)
        setUpAddRoutineButton(view)

        return view
    }

    private fun setUpRecyclerViewContent(adapter: RoutineAdapter) {
        sendRoutinesToAdapter(adapter)
    }

    private fun sendRoutinesToAdapter(adapter: RoutineAdapter) {
        routineViewModel.allRoutinesWithExercises.observe(viewLifecycleOwner, Observer { routines ->
            routines?.let { adapter.setRoutines(it) }
        })
    }

    private fun setUpAddRoutineButton(view: View) {
        val button: Button = view.findViewById(R.id.buttonAddRoutine)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.navigation_add_routine)
        }
    }
}


