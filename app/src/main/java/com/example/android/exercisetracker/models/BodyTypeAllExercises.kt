package com.example.android.exercisetracker.models

import androidx.room.Embedded
import androidx.room.Relation

class BodyTypeAllExercises {
    @Embedded
    var bodyType: BodyType? = null

    @Relation(parentColumn = "bodyTypeId", entityColumn = "bodyTypeId")
    var exercises: List<Exercise>? = null
}