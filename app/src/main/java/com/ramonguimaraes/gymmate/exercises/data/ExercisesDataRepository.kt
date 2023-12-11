package com.ramonguimaraes.gymmate.exercises.data

import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.ramonguimaraes.gymmate.exercises.domain.model.toExerciseDto
import com.ramonguimaraes.gymmate.exercises.domain.repository.ExercisesRepository
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.workout.domain.model.toWorkoutDto

class ExercisesDataRepository(private val dataSource: ExercisesDataSource) : ExercisesRepository {

    override suspend fun save(exercise: Exercise): Result<Exercise> {
        val dto = exercise.toExerciseDto()
        return dataSource.save(dto).mapResultSuccess { it.toExercise() }
    }

    override suspend fun update(exercise: Exercise): Result<Exercise> {
        val dto = exercise.toExerciseDto()
        return dataSource.update(dto).mapResultSuccess { it.toExercise() }
    }

    override suspend fun getAll(workoutId: String, userId: String): Result<List<Exercise>> {
        return dataSource.getAll(workoutId, userId).mapResultSuccess { list ->
            list.map { item -> item.toExercise() }
        }
    }

    override suspend fun delete(exercise: Exercise): Result<Unit> {
        return dataSource.delete(exercise.toExerciseDto())
    }
}