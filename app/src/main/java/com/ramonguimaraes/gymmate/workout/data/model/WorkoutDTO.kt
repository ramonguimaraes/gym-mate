package com.ramonguimaraes.gymmate.workout.data.model

import com.ramonguimaraes.gymmate.workout.domain.model.Workout

data class WorkoutDTO(
    var id: String = "",
    var name: String = "",
    var description: String = "",
    var userId: String = "",
)

fun WorkoutDTO.toMap(): Map<String, String> {
    return mapOf(
        "name" to name,
        "description" to description,
        "userId" to userId,
    )
}

fun WorkoutDTO.toWorkout(): Workout {
    return Workout(
        id = id,
        name = name,
        description = description
    )
}
