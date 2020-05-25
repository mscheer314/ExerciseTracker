package com.example.android.exercisetracker.utils

import android.view.MotionEvent
import androidx.recyclerview.selection.ItemDetailsLookup
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.adapters.AddRoutineExerciseAdapter

class ExerciseDetailsLookup(private val recyclerView: RecyclerView) :
    ItemDetailsLookup<Long>() {
    override fun getItemDetails(e: MotionEvent): ItemDetails<Long>? {
        val view = recyclerView.findChildViewUnder(e.x, e.y)
        if (view != null) {
            return (recyclerView.getChildViewHolder(view) as AddRoutineExerciseAdapter.ViewHolder)
                .getItemDetails()
        }
        return null
    }
}