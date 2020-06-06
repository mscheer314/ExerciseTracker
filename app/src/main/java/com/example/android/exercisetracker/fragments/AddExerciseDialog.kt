package com.example.android.exercisetracker.fragments

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
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
        builder.setPositiveButton("OK", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface, which: Int) {
                val exercise = Exercise(0, exerciseNameEditText.text.toString(), null)
                //TODO figure out how to add the exercise area
                exerciseViewModel.insert(exercise)
                dismiss()
            }
        })
        builder.setNegativeButton("CANCEL", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, which: Int) {
                dismiss()
            }
        })
        return builder.create()
    }
}