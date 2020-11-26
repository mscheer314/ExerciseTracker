package com.example.android.exercisetracker.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.WorkoutActivity
import com.example.android.exercisetracker.models.Exercise
import com.example.android.exercisetracker.models.RoutineWithExercises
import com.example.android.exercisetracker.models.Workout

class RoutineAdapter internal constructor(private val context: Context) :
    RecyclerView.Adapter<RoutineAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var routinesWithExercises: List<RoutineWithExercises> = emptyList()
    private var workout: Workout? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineName: TextView = itemView.findViewById(R.id.routine_title)
        val routineExercises: TextView = itemView.findViewById(R.id.routine_exercises)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = inflater.inflate(R.layout.routine_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return routinesWithExercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentRoutineWithExercises = routinesWithExercises[position]
        holder.routineName.text = currentRoutineWithExercises.routine.routineName
        holder.routineExercises.text =
            combineExerciseNamesIntoSingleString(currentRoutineWithExercises.exercises)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, WorkoutActivity::class.java)
            intent.putExtra("routineWithExercises", currentRoutineWithExercises)
            intent.putExtra("workout", workout)
            context.startActivity(intent)
        }
    }

    private fun combineExerciseNamesIntoSingleString(exercises: List<Exercise>): String {
        val builder = StringBuilder()
        exercises.forEach {
            builder.append(it.exerciseName)
            if (it != exercises.last())
                builder.append(", ")
        }
        return builder.toString()
    }

    fun setRoutines(routines: List<RoutineWithExercises>) {
        this.routinesWithExercises = routines
        notifyDataSetChanged()
    }

    fun setWorkout(workout: Workout) {
        this.workout = workout
    }

}
