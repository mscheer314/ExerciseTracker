package com.example.android.exercisetracker

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * A simple [Fragment] subclass.
 * Use the [ExerciseFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ExerciseFragment : Fragment() {
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
        val view = inflater.inflate(R.layout.fragment_exercise, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_exercises)
        /*recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = ExerciseAdapter(context)
        }*/
        val adapter = ExerciseAdapter(activity)
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        exerciseViewModel.allExercises.observe(viewLifecycleOwner, Observer { exercises ->
            exercises?.let { adapter.setExercises(it) }
        })

        val button: Button = view.findViewById(R.id.buttonAddExercise)
        button.setOnClickListener {
            val exercise = Exercise(1, "push up", "chest")
            exerciseViewModel.insert(exercise)
        }
        return view
    }
}
