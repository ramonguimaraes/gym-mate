package com.ramonguimaraes.gymmate.exercises.data

import android.net.Uri
import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise

data class ExerciseDTO(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var userId: String = "",
    var imgUri: Uri = Uri.EMPTY,
    var workoutId: String = ""
)

fun ExerciseDTO.toMap(): Map<String, Any> {
    return mapOf(
        "name" to name,
        "description" to description,
        "userId" to userId,
        "workoutId" to workoutId
    )
}

fun ExerciseDTO.toExercise(): Exercise {
    return Exercise(
        id = id,
        name = name,
        description = description,
        imgUri = imgUri,
        workoutId = workoutId,
        userId = userId
    )
}
