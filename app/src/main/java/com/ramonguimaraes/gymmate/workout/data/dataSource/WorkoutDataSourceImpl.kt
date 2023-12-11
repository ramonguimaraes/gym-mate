package com.ramonguimaraes.gymmate.workout.data.dataSource

import com.google.firebase.Timestamp
import com.google.firebase.firestore.FirebaseFirestore
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.workout.data.model.WorkoutDTO
import com.ramonguimaraes.gymmate.workout.data.model.toMap
import com.ramonguimaraes.gymmate.workout.presenter.ui.WorkoutDialog
import kotlinx.coroutines.tasks.await
import java.util.Date

class WorkoutDataSourceImpl(
    private val db: FirebaseFirestore
) : WorkoutDataSource {

    override suspend fun save(workout: WorkoutDTO): Result<WorkoutDTO> {
        return try {
            val result = db.collection("workouts").add(workout.toMap()).await()
            return if (result != null) {
                val id = result.id
                Result.Success(workout.copy(id = id))
            } else {
                throw Exception("Failed to save workout")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    override suspend fun delete(workout: WorkoutDTO): Result<Unit> {
        return try {
            db.collection("workouts").document(workout.id).delete().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    override suspend fun getAll(userId: String): Result<List<WorkoutDTO>> {
        return try {
            val resultList: MutableList<WorkoutDTO> = mutableListOf()
            val result = db.collection("workouts").whereEqualTo("userId", userId).get().await()

            result.forEach { snapshot ->
                val date = snapshot.get("date", Timestamp::class.java)?.toDate() ?: Date()
                resultList.add(snapshot.toObject(WorkoutDTO::class.java).copy(id = snapshot.id, date = date))
            }

            Result.Success(resultList)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    override suspend fun update(workout: WorkoutDTO): Result<WorkoutDTO> {
        return try {
            val docRef = db.collection("workouts").document(workout.id)
            docRef.update(workout.toMap()).await()
            Result.Success(workout)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }
}