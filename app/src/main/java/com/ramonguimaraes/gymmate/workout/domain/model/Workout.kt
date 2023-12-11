package com.ramonguimaraes.gymmate.workout.domain.model

import android.os.Parcelable
import com.ramonguimaraes.gymmate.workout.data.model.WorkoutDTO
import kotlinx.parcelize.Parcelize
import java.util.Date

@Parcelize
data class Workout(
    val id: String = "",
    val name: String,
    val description: String,
    val userId: String = "",
    val date: Date = Date()
): Parcelable

fun Workout.toWorkoutDto(): WorkoutDTO {
    return WorkoutDTO(
        id = id,
        name = name,
        description = description,
        userId = userId,
        date = date
    )
}
