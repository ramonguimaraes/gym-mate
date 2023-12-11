package com.ramonguimaraes.gymmate.workout.data.dataSource

import com.ramonguimaraes.gymmate.workout.data.model.WorkoutDTO
import com.ramonguimaraes.gymmate.core.utils.Result

interface WorkoutDataSource {
    suspend fun save(workout: WorkoutDTO): Result<WorkoutDTO>
    suspend fun delete(workout: WorkoutDTO): Result<Unit>
    suspend fun getAll(userId: String): Result<List<WorkoutDTO>>
    suspend fun update(workout: WorkoutDTO): Result<WorkoutDTO>
}
