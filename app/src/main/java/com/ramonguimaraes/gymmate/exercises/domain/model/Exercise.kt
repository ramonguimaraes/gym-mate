package com.ramonguimaraes.gymmate.exercises.domain.model

import android.net.Uri
import android.os.Parcelable
import com.ramonguimaraes.gymmate.exercises.data.ExerciseDTO
import kotlinx.parcelize.Parcelize

@Parcelize
data class Exercise(
    val id: String = "",
    val name: String,
    val description: String,
    val imgUri: Uri = Uri.EMPTY,
    val workoutId: String,
    val userId: String = ""
): Parcelable

fun Exercise.toExerciseDto(): ExerciseDTO {
    return ExerciseDTO(
        id = id,
        name = name,
        description = description,
        userId = userId,
        imgUri = imgUri,
        workoutId = workoutId
    )
}