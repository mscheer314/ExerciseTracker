package com.example.android.exercisetracker.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.RoutineWithExercises
import com.example.android.exercisetracker.models.WorkoutRowItem
import com.example.android.exercisetracker.models.WorkoutRowType

class WorkoutAdapter(private var routineWithExercises: RoutineWithExercises) :
    RecyclerView.Adapter<DefaultWorkoutViewHolder>() {
    private var adapterContents: MutableList<WorkoutRowItem> = mutableListOf<WorkoutRowItem>()

    init {
        routineWithExercises.exercises.forEach {
            adapterContents.add(WorkoutRowItem(WorkoutRowType.EXERCISE, it, null))
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
        if (workoutRow.type == WorkoutRowType.EXERCISE) {
            workoutRow.exercise?.let {
                holder.setText(
                    R.id.workout_exercise_title,
                    it.exerciseName
                )
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (adapterContents[position].type == WorkoutRowType.EXERCISE) {
            return WorkoutRowType.EXERCISE.ordinal
        } else {
            return WorkoutRowType.SET.ordinal
        }
    }

    class WorkoutRowDiffCallback(
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
    }
}