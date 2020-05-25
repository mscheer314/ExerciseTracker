package com.example.android.exercisetracker.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.DialogFragment
import com.example.android.exercisetracker.R

class AddExerciseDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val builder = AlertDialog.Builder(activity)
        builder.setTitle("Add Exercise")
        builder.setPositiveButton(
            "OK"
        ) { dialog, which -> dismiss() }
        builder.setNegativeButton(
            "CANCEL"
        ) { dialog, which -> dismiss() }

        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_exercise, null)
        builder.setView(view)
        return builder.create()
    }
}