package com.example.android.exercisetracker.adapters

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.*
import com.example.android.exercisetracker.models.Set
import kotlinx.android.synthetic.main.set_list_item.view.*
import kotlinx.android.synthetic.main.workout_exercise_list_item.view.*

class WorkoutAdapter(private val routineWithExercises: RoutineWithExercises) :
    RecyclerView.Adapter<DefaultWorkoutViewHolder>() {
    private lateinit var routineWithSets: RoutineWithSets
    private var workoutId: Int? = null
    private var adapterContents: MutableList<WorkoutRowItem> = mutableListOf<WorkoutRowItem>()
    private val NO_LBS = 0
    private val NO_REPS = 0
    private val AUTO_INCREMENTED = 0

    fun assignTypesToRowItems() {
        routineWithExercises.exercises.forEach { exercise ->
            adapterContents.add(WorkoutRowItem(WorkoutRowType.EXERCISE, exercise, null, false))
            if (this::routineWithSets.isInitialized) {
                routineWithSets.sets.forEach { set ->
                    if (set.exerciseId == exercise.exerciseId) {
                        adapterContents.add(WorkoutRowItem(WorkoutRowType.SET, null, set, false))
                    }
                }
            } else {
                adapterContents.add(
                    WorkoutRowItem(
                        WorkoutRowType.SET,
                        exercise,
                        workoutId?.let {
                            Set(
                                AUTO_INCREMENTED, NO_LBS, NO_REPS,
                                it,
                                routineWithExercises.routine.routineId, exercise.exerciseId
                            )
                        }
                        ,
                        false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultWorkoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView: View = when (viewType) {
            WorkoutRowType.EXERCISE.ordinal -> {
                layoutInflater.inflate(R.layout.workout_exercise_list_item, parent, false)
            }
            else -> layoutInflater.inflate(R.layout.set_list_item, parent, false)
        }
        if (viewType == WorkoutRowType.SET.ordinal) {
            inflatedView.tag = "Set Row"
        } else {
            inflatedView.tag = "Exercise Row"
        }

        return DefaultWorkoutViewHolder(
            inflatedView,
            MyEditTextListener(EditTextType.LBS),
            MyEditTextListener(EditTextType.REPS)
        )
    }

    override fun onBindViewHolder(holder: DefaultWorkoutViewHolder, position: Int) {
        holder.lbsTextListener.updatePosition(position)
        holder.repTextListener.updatePosition(position)
        val workoutRow: WorkoutRowItem = adapterContents[position]
        when (workoutRow.type) {
            WorkoutRowType.EXERCISE -> {
                setExerciseRowContent(workoutRow, holder)
            }
            WorkoutRowType.SET -> {
                setSetRowContent(holder, workoutRow, position)
            }
        }
    }

    private fun setExerciseRowContent(
        workoutRow: WorkoutRowItem,
        holder: DefaultWorkoutViewHolder
    ) {
        workoutRow.exercise?.let { exercise ->
            holder.setText(
                R.id.workout_exercise_title,
                exercise.exerciseName
            )

            holder.itemView.addSetButton.setOnClickListener {
                addSetRow(exercise)
            }
        }
    }

    private fun addSetRow(exerciseGettingSet: Exercise) {
        for (index in 0..adapterContents.size) {
            // if the index is at the end of the list or last of an exercise
            if (index + 1 == adapterContents.size
                || adapterContents[index + 1].type == WorkoutRowType.EXERCISE
                && exerciseGettingSet.exerciseId == adapterContents[index].exercise?.exerciseId
            ) {
                addToAdapterContents(index, exerciseGettingSet)
                break
            }
        }
    }

    private fun addToAdapterContents(
        index: Int,
        exerciseGettingSet: Exercise
    ) {
        adapterContents.add(
            index + 1,
            WorkoutRowItem(
                WorkoutRowType.SET,
                exerciseGettingSet,
                workoutId?.let {
                    Set(
                        index,
                        NO_LBS,
                        NO_REPS,
                        it,
                        routineWithExercises.routine.routineId,
                        exerciseGettingSet.exerciseId
                    )
                },
                false
            )
        )
        notifyDataSetChanged()
        return
    }

    private fun setSetRowContent(
        holder: DefaultWorkoutViewHolder,
        workoutRow: WorkoutRowItem,
        position: Int
    ) {
        holder.setText(R.id.lbsEditText, workoutRow.set?.lbs.toString())
        holder.setText(R.id.repsEditText, workoutRow.set?.reps.toString())

        // if workoutrow is completed, make the background color purple
        if (workoutRow.isCompleted) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.colorRowHighlight)
            )
        }
        // if the workout row isn't completed, make the background white
        if (!workoutRow.isCompleted) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(holder.itemView.context, R.color.colorRowWhite)
            )
        }

        if (position != 0) {
            if (adapterContents[position - 1].type == WorkoutRowType.EXERCISE || adapterContents[position - 1].isCompleted) {
                holder.itemView.finishedButton.isEnabled = true
            } else {
                holder.itemView.finishedButton.isEnabled = false
            }
        }

        //   holder.itemView.finishedButton.isEnabled = adapterContents[position - 1].isCompleted ||
        //           adapterContents[position - 1].type == WorkoutRowType.EXERCISE

        holder.itemView.finishedButton.setOnClickListener {
            workoutRow.isCompleted = true
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorRowHighlight
                )
            )
            notifyDataSetChanged()
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (adapterContents[position].type == WorkoutRowType.EXERCISE) {
            WorkoutRowType.EXERCISE.ordinal
        } else {
            WorkoutRowType.SET.ordinal
        }
    }

    override fun getItemCount() = adapterContents.size

    fun setRoutineWithSets(routineWithSets: RoutineWithSets) {
        this.routineWithSets = routineWithSets
        notifyDataSetChanged()
    }

    fun getWorkoutSets(): List<Set> {
        val sets: MutableList<Set> = mutableListOf()
        adapterContents.forEach { workoutRowItem ->
            if (workoutRowItem.type == WorkoutRowType.SET) {
                workoutRowItem.set?.let { sets.add(it) }
            }
        }
        return sets
    }

    fun setWorkoutId(workoutId: Int) {
        this.workoutId = workoutId
    }

    inner class MyEditTextListener(private var editTextType: EditTextType) : TextWatcher {
        var position = 0
        fun updatePosition(position: Int) {
            this.position = position
        }

        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            when (editTextType) {
                EditTextType.REPS -> if (!s.isNullOrBlank())
                    adapterContents[position].set?.reps = s.toString().toInt()
                EditTextType.LBS -> if (!s.isNullOrBlank())
                    adapterContents[position].set?.lbs = s.toString().toInt()
            }
        }

        override fun afterTextChanged(s: Editable?) {}
    }

    enum class EditTextType {
        LBS,
        REPS
    }
}