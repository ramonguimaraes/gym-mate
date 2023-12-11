package com.ramonguimaraes.gymmate.exercises.domain.repository

import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.exercises.data.ExerciseDTO

interface ExercisesRepository {
    suspend fun save(exercise: Exercise): Result<Exercise>
    suspend fun update(exercise: Exercise): Result<Exercise>
    suspend fun getAll(workoutId: String, userId: String): Result<List<Exercise>>
    suspend fun delete(exercise: Exercise): Result<Unit>
}