package com.example.android.exercisetracker.adapters

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

class WorkoutAdapter(private var routineWithExercises: RoutineWithExercises) :
    RecyclerView.Adapter<DefaultWorkoutViewHolder>() {
    private lateinit var routineWithSets: RoutineWithSets
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
                        Set(
                            AUTO_INCREMENTED, NO_LBS, NO_REPS,
                            routineWithExercises.routine.routineId, exercise.exerciseId

                        ),
                        false
                    )
                )
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DefaultWorkoutViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)

        val inflatedView: View = when (viewType) {
            WorkoutRowType.EXERCISE.ordinal ->
                layoutInflater.inflate(R.layout.workout_exercise_list_item, parent, false)
            else -> layoutInflater.inflate(R.layout.set_list_item, parent, false)
        }
        return DefaultWorkoutViewHolder(inflatedView)
    }

    override fun getItemCount(): Int {
        return adapterContents.size
    }

    override fun onBindViewHolder(holder: DefaultWorkoutViewHolder, position: Int) {
        val workoutRow: WorkoutRowItem = adapterContents[position]
        when (workoutRow.type) {
            WorkoutRowType.EXERCISE -> {
                setExerciseRowContent(workoutRow, holder)
            }
            WorkoutRowType.SET -> {
                setSetRowContent(holder, workoutRow)
            }
        }
    }

    private fun setSetRowContent(
        holder: DefaultWorkoutViewHolder,
        workoutRow: WorkoutRowItem
    ) {
        holder.setText(R.id.lbsEditText, workoutRow.set?.lbs.toString())
        holder.setText(R.id.repsEditText, workoutRow.set?.reps.toString())
        if (workoutRow.isCompleted) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorRowHighlight
                )
            )
        }
        if (!workoutRow.isCompleted) {
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorRowWhite
                )
            )
        }

        holder.itemView.finishedButton.setOnClickListener {
            workoutRow.isCompleted = true
            holder.itemView.setBackgroundColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.colorRowHighlight
                )
            )
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
            if (adapterContents[index].exercise?.exerciseId == exerciseGettingSet.exerciseId
                && adapterContents[index] == adapterContents.lastOrNull()
            ) {
                addToAdapterContents(index, exerciseGettingSet)
                break
            }
            if (adapterContents[index].exercise?.exerciseId == exerciseGettingSet.exerciseId
                && adapterContents[index + 1].exercise?.exerciseId != exerciseGettingSet.exerciseId
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
                Set(
                    AUTO_INCREMENTED,
                    NO_LBS,
                    NO_REPS,
                    routineWithExercises.routine.routineId,
                    exerciseGettingSet.exerciseId

                ),
                false
            )
        )
        notifyDataSetChanged()
        return
    }

    override fun getItemViewType(position: Int): Int {
        return if (adapterContents[position].type == WorkoutRowType.EXERCISE) {
            WorkoutRowType.EXERCISE.ordinal
        } else {
            WorkoutRowType.SET.ordinal
        }
    }

    /*class WorkoutRowDiffCallback(
        private val newRows: List<WorkoutRowItem>,
        private val oldRows: List<WorkoutRowItem>
    ) : DiffUtil.Callback() {
        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow.type == newRow.type
        }

        override fun getOldListSize(): Int = oldRows.size

        override fun getNewListSize(): Int = newRows.size

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            val oldRow = oldRows[oldItemPosition]
            val newRow = newRows[newItemPosition]
            return oldRow == newRow
        }
    }*/

    fun setRoutineWithSets(routineWithSets: RoutineWithSets) {
        this.routineWithSets = routineWithSets
        notifyDataSetChanged()
    }
}