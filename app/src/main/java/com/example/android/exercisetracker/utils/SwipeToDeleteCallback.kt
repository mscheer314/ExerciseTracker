package com.example.android.exercisetracker.utils

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.android.exercisetracker.adapters.HistoryAdapter
import kotlinx.coroutines.runBlocking


open class SwipeToDeleteCallback : ItemTouchHelper.SimpleCallback {
    private var historyAdapter: HistoryAdapter? = null
    private var type: Int

    constructor(adapter: HistoryAdapter?) : super(
        0,
        ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT
    ) {
        historyAdapter = adapter
        type = 1
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        when (type) {

            1 -> runBlocking { historyAdapter?.delete(position) }
        }
    }
}