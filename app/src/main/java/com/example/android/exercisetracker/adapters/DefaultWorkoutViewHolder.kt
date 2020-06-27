package com.example.android.exercisetracker.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import java.util.*

class DefaultWorkoutViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val viewMap: MutableMap<Int, View> = HashMap()

    init {
        findViewItems(itemView)
    }

    fun setText(
        @IdRes id: Int,
        text: String
    ) {
        val view =
            (viewMap[id] ?: throw IllegalArgumentException("View for $id not found")) as? TextView
                ?: throw IllegalArgumentException("View for $id is not a TextView")
        view.text = text
    }

    private fun findViewItems(itemView: View) {
        addToMap(itemView)
        if (itemView is ViewGroup) {
            val childCount = itemView.childCount
            (0 until childCount)
                .map { itemView.getChildAt(it) }
                .forEach { findViewItems(it) }
        }
    }

    private fun addToMap(itemView: View) {
        if (itemView.id == View.NO_ID) {
            itemView.id = View.generateViewId()
        }
        viewMap.put(itemView.id, itemView)
    }
}