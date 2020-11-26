package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Exercise
import com.example.android.exercisetracker.models.Set
import com.example.android.exercisetracker.models.Workout
import com.example.android.exercisetracker.viewmodels.WorkoutViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class HistoryAdapter internal constructor(context: Context, workoutViewModel: WorkoutViewModel) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private val workoutViewModel: WorkoutViewModel = workoutViewModel
    private var workouts: List<Workout> = emptyList()
    private var sets: List<Set> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutDate: TextView = itemView.findViewById(R.id.history_date)
        val workoutExercises: TextView = itemView.findViewById(R.id.history_exercise_name)
        val workoutSets: TextView = itemView.findViewById(R.id.history_set_info)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.history_list_item, parent, false)

        return ViewHolder(itemView)
    }

    override fun getItemCount() = workouts.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.workoutDate.text = workouts[position].date.toString()

        var exerciseText = ""
        var setText = ""
        val exerciseList = mutableListOf<Exercise?>()

        for (set in sortSets(sets)) {
            if (set.workoutId == workouts[position].workoutId && !exerciseList.contains(set.exercise)) {
                exerciseList.add(set.exercise)
            }
            if (set.workoutId == workouts[position].workoutId) {
                exerciseList.add(null)
            }
        }
        exerciseList.forEach {
            exerciseText += it?.exerciseName ?: "\n"
        }
        holder.workoutExercises.text = exerciseText

        for (set in sortSets(sets)) {
            if (workouts[position].workoutId == set.workoutId) {
                setText = setText + set.lbs + " x " + set.reps + "\n"
            }
        }

        holder.workoutSets.text = setText
    }


    private fun sortSets(sets: List<Set>): List<Set> {
        // if sets size is 1, it doesn't need to be sorted
        if (sets.size < 2)
            return sets

        // create list to return as result
        val resultList = mutableListOf<Set>()

        // get the workoutIds of the sets list
        val workoutIds = mutableListOf<Int>()
        for (set in sets) {
            if (!workoutIds.contains(set.workoutId)) {
                workoutIds.add(set.workoutId)
            }
        }

        // sort sets by each workoutId
        for (workoutId in workoutIds) {
            // get the sets for that workoutId
            val setsOfWorkout = mutableListOf<Set>()
            for (set in sets) {
                if (set.workoutId == workoutId)
                    setsOfWorkout.add(set)
            }

            quickSortSetsOfExercise(setsOfWorkout).forEach {
                resultList.add(it)
            }
        }
        return resultList
    }

    private fun quickSortSetsOfExercise(setsOfWorkout: MutableList<Set>): List<Set> {
        val pivot = setsOfWorkout[setsOfWorkout.size / 2]
        // group the sets by exercise
        val equal = setsOfWorkout.filter { it.exercise.exerciseId == pivot.exercise.exerciseId }
        val less = setsOfWorkout.filter { it.exercise.exerciseId < pivot.exercise.exerciseId }
        val greater = setsOfWorkout.filter { it.exercise.exerciseId > pivot.exercise.exerciseId }
        return sortSets(less) + equal + sortSets(greater)
    }

    fun setWorkouts(workouts: List<Workout>) {
        this.workouts = workouts
        notifyDataSetChanged()
    }

    fun setSets(sets: List<Set>) {
        this.sets = sets
    }

    suspend fun delete(position: Int) {
        withContext(Dispatchers.IO) {
            workoutViewModel.delete(workouts[position])
        }
    }
}
