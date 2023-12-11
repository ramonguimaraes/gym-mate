package com.ramonguimaraes.gymmate.workout.domain.repository

import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import com.ramonguimaraes.gymmate.core.utils.Result

interface WorkoutRepository {
    suspend fun save(workout: Workout, userId: String): Result<Workout>
    suspend fun delete(workout: Workout): Result<Unit>
    suspend fun getAll(userId: String): Result<List<Workout>>
    suspend fun update(workout: Workout): Result<Workout>
}
