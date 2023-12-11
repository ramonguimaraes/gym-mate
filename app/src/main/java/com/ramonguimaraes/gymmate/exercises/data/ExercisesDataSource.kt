package com.ramonguimaraes.gymmate.exercises.data

import com.ramonguimaraes.gymmate.core.utils.Result

interface ExercisesDataSource {
    suspend fun save(exercise: ExerciseDTO): Result<ExerciseDTO>
    suspend fun getAll(workoutId: String, userId: String): Result<List<ExerciseDTO>>
    suspend fun update(exercise: ExerciseDTO): Result<ExerciseDTO>
    suspend fun delete(exercise: ExerciseDTO): Result<Unit>
}