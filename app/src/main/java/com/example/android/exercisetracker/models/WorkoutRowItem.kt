package com.example.android.exercisetracker.models

data class WorkoutRowItem(
    var type: WorkoutRowType,
    var exercise: Exercise?,
    var set: Set?
)

enum class WorkoutRowType {
    EXERCISE,
    SET
}