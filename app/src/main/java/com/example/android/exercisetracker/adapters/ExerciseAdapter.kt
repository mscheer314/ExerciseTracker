package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Exercise

class ExerciseAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exercises: List<Exercise> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView = itemView.findViewById(R.id.exercise_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.exercise_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exercises.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = exercises[position]
        holder.exerciseTitle.text = current.exerciseName

        holder.itemView.setOnClickListener {
            Toast.makeText(
                holder.exerciseTitle.context,
                "In Exercise List",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun setExercises(exercises: List<Exercise>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }
}