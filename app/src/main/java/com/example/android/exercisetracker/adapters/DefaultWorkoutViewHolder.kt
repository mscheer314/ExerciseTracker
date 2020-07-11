package com.example.android.exercisetracker.adapters

import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.annotation.IdRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.set_list_item.view.*
import java.util.*

class DefaultWorkoutViewHolder(
    itemView: View,
    lbsTextListener: WorkoutAdapter.MyEditTextListener,
    repTextListener: WorkoutAdapter.MyEditTextListener
) :
    RecyclerView.ViewHolder(itemView) {

    private val viewMap: MutableMap<Int, View> = HashMap()
    val lbsTextListener: WorkoutAdapter.MyEditTextListener
    val repTextListener: WorkoutAdapter.MyEditTextListener


    init {
        this.lbsTextListener = lbsTextListener
        this.repTextListener = repTextListener
        if (itemView.tag == "Set Row") {
            val repsText: EditText = itemView.repsEditText
            val lbsText: EditText = itemView.lbsEditText
            lbsText.addTextChangedListener(lbsTextListener)
            repsText.addTextChangedListener(repTextListener)
        }
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