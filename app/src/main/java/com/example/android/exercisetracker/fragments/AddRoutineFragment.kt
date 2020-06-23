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
import com.example.android.exercisetracker.models.Routine
import com.example.android.exercisetracker.models.RoutineExerciseCrossRef
import com.example.android.exercisetracker.utils.ExerciseDetailsLookup
import com.example.android.exercisetracker.utils.Keyboard
import com.example.android.exercisetracker.utils.MyItemKeyProvider
import com.example.android.exercisetracker.viewmodels.ExerciseViewModel
import com.example.android.exercisetracker.viewmodels.RoutineViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext


class AddRoutineFragment : Fragment(), CoroutineScope {
    private lateinit var exerciseViewModel: ExerciseViewModel
    private lateinit var routineViewModel: RoutineViewModel

    var tracker: SelectionTracker<Long>? = null
    var routineId: Int? = null

    companion object;

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_add_routine, container, false)
        val activity = activity as Context

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_add_routine_exercises)
        val routineTitleEditText = view.findViewById<EditText>(R.id.routineTitleEditText)
        setUpHideKeyboardWhenNotTyping(routineTitleEditText)
        val addRoutineSaveButton = view.findViewById<Button>(R.id.addRoutineSaveButton)
        val adapter = AddRoutineExerciseAdapter(activity)

        setUpSaveButton(addRoutineSaveButton, routineTitleEditText, adapter, view)
        setUpRecyclerView(recyclerView, activity, adapter)
        setUpTracker(recyclerView, adapter)

        return view
    }

    private fun setUpHideKeyboardWhenNotTyping(routineTitleEditText: EditText) {
        routineTitleEditText.setOnFocusChangeListener { v, _ ->
            context?.let { view?.let { it1 -> Keyboard.hideSoftKeyBoard(it, it1) } }
        }
    }

    private fun setUpSaveButton(
        addRoutineSaveButton: Button,
        routineTitleEditText: EditText,
        adapter: AddRoutineExerciseAdapter,
        view: View
    ) {
        addRoutineSaveButton.setOnClickListener {
            if (routineTitleEditText.text.isEmpty()) {
                showErrorMessage()
            } else {
                routineViewModel = ViewModelProvider(this).get(RoutineViewModel::class.java)

                val title = routineTitleEditText.text.toString()
                val routine = Routine(0, title)
                val selectedExercises = getSelectedExercises()
                if (selectedExercises != null) {
                    for (i in selectedExercises.indices) {
                        selectedExercises[i] =
                            adapter.getExerciseIdFromSelectionId(selectedExercises[i])
                    }
                    launch {
                        insertRoutineToDatabase(routine)
                        insertRoutineExerciseJoinsToDatabase(selectedExercises)
                    }
                }
                closeKeyboard(view)
            }
            view.findNavController().navigate(R.id.navigation_start_workout)
        }
    }

    private fun getSelectedExercises(): MutableList<Int>? {
        return tracker?.selection?.map { it.toInt() }?.toMutableList()
    }

    private fun insertRoutineExerciseJoinsToDatabase(selectedExercises: MutableList<Int>) {
        selectedExercises.forEach {
            val routineExerciseCrossRef =
                routineId?.let { routine ->
                    RoutineExerciseCrossRef(
                        routine,
                        it
                    )
                }
            if (routineExerciseCrossRef != null) {
                launch {
                    routineViewModel.insert(routineExerciseCrossRef)
                }
            }
        }
    }

    private suspend fun insertRoutineToDatabase(routine: Routine) {
        routineId = routineViewModel.insert(routine)
    }

    private fun showErrorMessage() {
        Toast.makeText(
            activity,
            "Please enter a routine title",
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun closeKeyboard(view: View) {
        context?.let { it1 -> Keyboard.hideSoftKeyBoard(it1, view) }
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
        tracker = SelectionTracker.Builder(
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
