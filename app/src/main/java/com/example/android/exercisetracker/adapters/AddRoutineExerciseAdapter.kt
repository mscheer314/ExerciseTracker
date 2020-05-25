package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.selection.SelectionTracker
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Exercise

class AddRoutineExerciseAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<AddRoutineExerciseAdapter.ViewHolder>() {

    init {
        setHasStableIds(true)
    }

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exercises: List<Exercise> = emptyList<Exercise>()
    var tracker: SelectionTracker<Long>? = null

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val exerciseTitle: TextView = itemView.findViewById(R.id.exercise_title)
        val exerciseArea: TextView = itemView.findViewById(R.id.exercise_area)

        fun getItemDetails(): ItemDetailsLookup.ItemDetails<Long> =
            object : ItemDetailsLookup.ItemDetails<Long>() {
                override fun getPosition(): Int = adapterPosition
                override fun getSelectionKey(): Long? = itemId
            }

        fun bind(value: Exercise, isActivated: Boolean = false) {
            exerciseTitle.text = value.toString()
            itemView.isActivated = isActivated
        }
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
        holder.exerciseArea.text = current.exerciseBodyType

        tracker?.let {
            holder.bind(
                current,
                it.isSelected(position.toLong())
            )
        }
        /*holder.itemView.setOnClickListener {
        Toast.makeText(
            holder.exerciseTitle.context,
            "In Add Routine",
            Toast.LENGTH_LONG
        ).show()
    }*/

    }

    override fun getItemId(position: Int): Long = position.toLong()

    fun setExercises(exercises: List<Exercise>) {
        this.exercises = exercises
        notifyDataSetChanged()
    }
}