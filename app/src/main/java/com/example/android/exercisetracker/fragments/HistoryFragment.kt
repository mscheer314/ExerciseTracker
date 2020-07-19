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
import com.example.android.exercisetracker.adapters.HistoryAdapter
import com.example.android.exercisetracker.viewmodels.SetViewModel
import com.example.android.exercisetracker.viewmodels.WorkoutViewModel

class HistoryFragment : Fragment() {
    private lateinit var workoutViewModel: WorkoutViewModel
    private lateinit var setViewModel: SetViewModel

    companion object {
        fun newInstance(): HistoryFragment {
            return HistoryFragment()
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_history, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_history)
        val adapter = HistoryAdapter(activity)

        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter

        workoutViewModel = ViewModelProvider(this).get(WorkoutViewModel::class.java)
        workoutViewModel.allWorkouts.observe(viewLifecycleOwner, Observer { workouts ->
            workouts?.let { adapter.setWorkouts(it) }
        })

        setViewModel = ViewModelProvider(this).get(SetViewModel::class.java)
        setViewModel.allSets.observe(viewLifecycleOwner, Observer { sets ->
            sets?.let { adapter.setSets(it) }
        })
        return view
    }
}