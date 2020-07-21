package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Exercise

class ExerciseAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<ExerciseAdapter.ViewHolder>(), Filterable {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var exerciseList: List<Exercise> = emptyList()
    var exerciseFilterList = mutableListOf<Exercise>()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        init {
            exerciseFilterList = exerciseList as MutableList<Exercise>
        }

        val exerciseTitle: TextView = itemView.findViewById(R.id.exercise_title)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.exercise_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exerciseFilterList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = exerciseFilterList[position]
        holder.exerciseTitle.text = current.exerciseName
    }

    fun setExercises(exercises: List<Exercise>) {
        this.exerciseList = exercises
        exerciseFilterList = exercises as MutableList<Exercise>
        notifyDataSetChanged()
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charSearch = constraint.toString()
                if (charSearch.isEmpty()) {
                    exerciseFilterList = exerciseList as MutableList<Exercise>
                } else {
                    val resultList = mutableListOf<Exercise>()
                    for (row in exerciseList) {
                        if (row.exerciseName.toLowerCase().contains(charSearch.toLowerCase())) {
                            resultList.add(row)
                        }
                    }
                    exerciseFilterList = resultList
                }
                val filterResults = FilterResults()
                filterResults.values = exerciseFilterList
                return filterResults
            }

            @Suppress("UNCHECKED_CAST")
            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                exerciseFilterList = results?.values as MutableList<Exercise>
                notifyDataSetChanged()
            }
        }
    }
}