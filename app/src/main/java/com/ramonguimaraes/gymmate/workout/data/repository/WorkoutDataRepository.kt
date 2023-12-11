package com.ramonguimaraes.gymmate.workout.data.repository

import com.ramonguimaraes.gymmate.workout.data.dataSource.WorkoutDataSource
import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import com.ramonguimaraes.gymmate.workout.domain.model.toWorkoutDto
import com.ramonguimaraes.gymmate.workout.domain.repository.WorkoutRepository
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.workout.data.model.toWorkout

class WorkoutDataRepository(private val dataSource: WorkoutDataSource): WorkoutRepository {

    override suspend fun save(workout: Workout, userId: String): Result<Workout> {
        val dto = workout.toWorkoutDto(userId)
        return dataSource.save(dto).mapResultSuccess { it.toWorkout() }
    }

    override suspend fun delete(workout: Workout): Result<Unit> {
        return dataSource.delete(workout.toWorkoutDto())
    }

    override suspend fun getAll(userId: String): Result<List<Workout>> {
        return dataSource.getAll(userId).mapResultSuccess { list ->
            list.map { item -> item.toWorkout() } }
    }

    override suspend fun update(workout: Workout): Result<Workout> {
        val dto = workout.toWorkoutDto()
        return dataSource.update(dto) .mapResultSuccess { it.toWorkout() }
    }
}
