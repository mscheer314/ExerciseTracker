package com.example.android.exercisetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.adapters.WorkoutRoutineAdapter
import com.example.android.exercisetracker.viewmodels.WorkoutRoutineViewModel

class StartWorkoutFragment: Fragment() {
    private lateinit var workoutRoutineViewModel: WorkoutRoutineViewModel

    companion object {
        fun newInstance(): StartWorkoutFragment {
            return StartWorkoutFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_start_workout, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_routines)
        val adapter = WorkoutRoutineAdapter(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        return view
    }
}