package com.example.android.exercisetracker.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.recyclerview.selection.SelectionPredicates
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.selection.StorageStrategy
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.adapters.AddRoutineExerciseAdapter
import com.example.android.exercisetracker.models.ExerciseRoutineJoin
import com.example.android.exercisetracker.models.Routine
import com.example.android.exercisetracker.utils.ExerciseDetailsLookup
import com.example.android.exercisetracker.utils.Keyboard
import com.example.android.exercisetracker.utils.MyItemKeyProvider
import com.example.android.exercisetracker.viewmodels.ExerciseRoutineJoinViewModel
import com.example.android.exercisetracker.viewmodels.ExerciseViewModel
import com.example.android.exercisetracker.viewmodels.RoutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AddRoutineFragment : Fragment(), CoroutineScope {
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var routineViewModel: RoutineViewModel
    private lateinit var exerciseRoutineJoinViewModel: ExerciseRoutineJoinViewModel

    var tracker: SelectionTracker<Long>? = null
    var routineId: Long? = null

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
        val routineTitleEditText = view.findViewById<EditText>(R.id.routineTitleEditText)
        val addRoutineSaveButton = view.findViewById<Button>(R.id.addRoutineSaveButton)
        val adapter = AddRoutineExerciseAdapter(activity)
        addRoutineSaveButton.setOnClickListener {
            if (routineTitleEditText.text.isEmpty()) {
                Toast.makeText(
                    getActivity(),
                    "Please enter a routine title",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)
                exerciseRoutineJoinViewModel =
                    ViewModelProvider(this).get(ExerciseRoutineJoinViewModel::class.java)

                val title = routineTitleEditText.text.toString()
                val routine = Routine(0, title)
                var selectedExercises = tracker?.selection?.map { it.toInt() }?.toMutableList()
                if (selectedExercises != null) {
                    for (i in selectedExercises.indices) {
                        selectedExercises[i] =
                            adapter.getExerciseIdFromSelectionId(selectedExercises[i])
                    }
                    launch {
                        routineId = routineViewModel.insert(routine)
                        selectedExercises.forEach {
                            val exerciseRoutineJoin =
                                routineId?.toLong()?.let { it1 ->
                                    ExerciseRoutineJoin(
                                        it.toLong(),
                                        it1
                                    )
                                }
                            if (exerciseRoutineJoin != null) {
                                exerciseRoutineJoinViewModel.insert(exerciseRoutineJoin)
                            }
                        }
                    }
                }
                context?.let { it1 -> Keyboard.hideSoftKeyBoard(it1, view) }


            }
            view.findNavController().navigate(R.id.navigation_start_workout)
        }

        setUpRecyclerView(recyclerView, activity, adapter)
        setUpTracker(recyclerView, adapter)


        return view
    }

    private fun setUpRecyclerView(
        recyclerView: RecyclerView,
        activity: Context,
        adapter: AddRoutineExerciseAdapter
    ) {
        recyclerView.layoutManager = LinearLayoutManager(activity)
        recyclerView.adapter = adapter
        exerciseViewModel = ViewModelProvider(this).get(ExerciseViewModel::class.java)
        exerciseViewModel.allExercises.observe(viewLifecycleOwner, Observer { exercises ->
            exercises?.let { adapter.setExercises(it) }
        })
    }

    private fun setUpTracker(
        recyclerView: RecyclerView,
        adapter: AddRoutineExerciseAdapter
    ) {
        tracker = SelectionTracker.Builder<Long>(
            "mySelection",
            recyclerView,
            MyItemKeyProvider(recyclerView),
            ExerciseDetailsLookup(recyclerView),
            StorageStrategy.createLongStorage()
        ).withSelectionPredicate(
            SelectionPredicates.createSelectAnything()
        ).build()

        adapter.tracker = tracker
    }

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.IO
}
