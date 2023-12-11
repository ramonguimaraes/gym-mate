package com.ramonguimaraes.gymmate.workout.data.model

import com.google.firebase.Timestamp
import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import java.util.Date

data class WorkoutDTO(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var userId: String = "",
    val date: Date = Date()
)

fun WorkoutDTO.toMap(): Map<String, Any> {
    return mapOf(
        "name" to name,
        "description" to description,
        "userId" to userId,
        "date" to Timestamp(date)
    )
}

fun WorkoutDTO.toWorkout(): Workout {
    return Workout(
        id = id,
        name = name,
        description = description,
        userId = userId,
        date = date
    )
}
