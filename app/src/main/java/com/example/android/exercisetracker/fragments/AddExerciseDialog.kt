package com.example.android.exercisetracker.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.ViewModelProvider
import com.example.android.exercisetracker.R
import com.example.android.exercisetracker.models.Exercise
import com.example.android.exercisetracker.viewmodels.ExerciseViewModel

class AddExerciseDialog : DialogFragment() {
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val exerciseNameEditText: EditText
        val exerciseViewModel: ExerciseViewModel =
            ViewModelProvider(this).get(ExerciseViewModel::class.java)

        val builder = AlertDialog.Builder(activity)
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.dialog_add_exercise, null)
        builder.setView(view)
        builder.setTitle("Add Exercise")
        exerciseNameEditText = view.findViewById(R.id.exerciseAddTitle)
        builder.setPositiveButton(
            "OK"
        ) { _, _ ->
            if (isAlpha(exerciseNameEditText.text.toString())) {
                val exercise = Exercise(0, exerciseNameEditText.text.toString())
                exerciseViewModel.insert(exercise)
            }
            dismiss()
        }
        builder.setNegativeButton(
            "CANCEL"
        ) { _, _ -> dismiss() }
        return builder.create()
    }

    private fun isAlpha(input: String): Boolean {
        val pattern = "[a-zA-Z]+".toRegex()
        return input.matches(pattern)
    }
}