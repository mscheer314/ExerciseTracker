package com.example.android.exercisetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.adapters.RoutineAdapter
import com.example.android.exercisetracker.viewmodels.RoutineViewModel

class StartWorkoutFragment : Fragment() {
    private lateinit var routineViewModel: RoutineViewModel

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
        val adapter = RoutineAdapter(activity)
        recyclerView.layoutManager = GridLayoutManager(activity, 2)
        recyclerView.adapter = adapter

        routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
        routineViewModel.allRoutines.observe(viewLifecycleOwner, Observer { routines ->
            routines?.let { adapter.setRoutines(it) }
        })

        var routineTitle: TextView = view.findViewById(R.id.routineTitleTextView)
        var routineExercises: TextView = view.findViewById(R.id.RoutineExercisesTextView)

        /* val button: Button = view.findViewById(R.id.buttonAddRoutine)
         button.setOnClickListener {
             val routine = Routine(routineTitle.text.toString(), routineExercises.text.toString())
             routineViewModel.insert(routine)
         }*/

        val button: Button = view.findViewById(R.id.buttonAddRoutine)
        button.setOnClickListener {
            view.findNavController().navigate(R.id.navigation_add_routine)
        }

        return view
    }
}