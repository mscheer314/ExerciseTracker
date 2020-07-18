package com.example.android.exercisetracker.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Set

class HistoryAdapter internal constructor(context: Context) :
    RecyclerView.Adapter<HistoryAdapter.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var sets: List<Set> = emptyList()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val workoutDate: TextView = itemView.findViewById(R.id.date)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView = inflater.inflate(R.layout.history_list_item, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount() = sets.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val current = sets[position]
    }

    fun setSets(sets: List<Set>) {
        this.sets = sets
        notifyDataSetChanged()
    }
}