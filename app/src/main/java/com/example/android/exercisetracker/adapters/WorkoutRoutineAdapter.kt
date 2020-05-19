package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.WorkoutRoutine

class WorkoutRoutineAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<WorkoutRoutineAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var routines: List<WorkoutRoutine> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val routineName: TextView = itemView.findViewById(R.id.routine_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView: View = inflater.inflate(R.layout.workout_routine_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return routines.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = routines[position]
        holder.routineName.text = current.workoutRoutineName
    }

    fun setWorkoutRoutines(workoutRoutines: List<WorkoutRoutine>) {
        this.routines = workoutRoutines
        notifyDataSetChanged()
    }


}
