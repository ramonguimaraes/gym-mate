package com.ramonguimaraes.gymmate.workout.domain.model

import com.ramonguimaraes.gymmate.workout.data.model.WorkoutDTO

data class Workout(
    val id: String = "",
    val name: String,
    val description: String
)

fun Workout.toWorkoutDto(userId: String = ""): WorkoutDTO {
    return WorkoutDTO(
        id = id,
        name = name,
        description = description,
        userId = userId
    )
}
