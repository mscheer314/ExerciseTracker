package com.example.android.exercisetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.adapters.ExerciseAdapter
import com.example.android.exercisetracker.viewmodels.ExerciseViewModel

class AddRoutineFragment : Fragment() {
    private lateinit var exerciseViewModel: ExerciseViewModel

    companion object {
        fun newInstance(): ExerciseFragment {
            return ExerciseFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_routine, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_add_routine_exercises)
        val adapter = ExerciseAdapter(activity)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        exerciseViewModel.allExercises.observe(viewLifecycleOwner, Observer { exercises ->
            exercises?.let { adapter.setExercises(it) }
        })

        return view
    }
}
