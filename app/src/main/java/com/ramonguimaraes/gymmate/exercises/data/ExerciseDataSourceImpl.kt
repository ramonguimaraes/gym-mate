package com.ramonguimaraes.gymmate.exercises.data

import android.net.Uri
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QueryDocumentSnapshot
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.StorageTask
import com.google.firebase.storage.UploadTask
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.workout.data.model.WorkoutDTO
import com.ramonguimaraes.gymmate.workout.data.model.toMap
import kotlinx.coroutines.tasks.await

class ExerciseDataSourceImpl(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : ExercisesDataSource {

    override suspend fun save(exercise: ExerciseDTO): Result<ExerciseDTO> {
        return try {
            val result = db.collection("exercises").add(exercise.toMap()).await()
            return if (result != null) {
                val id = result.id
                if (exercise.imgUri != Uri.EMPTY) {
                    val downloadUir = uploadPhoto(exercise.imgUri, id)
                    exercise.imgUri = downloadUir
                }
                Result.Success(exercise.copy(id = id))
            } else {
                throw Exception("Failed to save exercise")
            }
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    override suspend fun getAll(workoutId: String, userId: String): Result<List<ExerciseDTO>> {
        return try {
            val resultList: MutableList<ExerciseDTO> = mutableListOf()
            val result = db.collection("exercises")
                .whereEqualTo("workoutId", workoutId)
                .whereEqualTo("userId", userId).get().await()
            result.forEach { snapshot ->
                resultList.add(snapshot.toObject(ExerciseDTO::class.java).copy(id = snapshot.id, imgUri = getUriImage(snapshot)))
            }

            Result.Success(resultList)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    private suspend fun getUriImage(snapshot: QueryDocumentSnapshot): Uri {
        return try {
            storage.reference.child(snapshot.id).downloadUrl.await()
        } catch (e: Exception) {
            Uri.EMPTY
        }
    }

    override suspend fun update(exercise: ExerciseDTO): Result<ExerciseDTO> {
        return try {
            val docRef = db.collection("exercises").document(exercise.id)
            docRef.update(exercise.toMap()).await()
            if (exercise.imgUri != Uri.EMPTY) {
                val downloadUir = uploadPhoto(exercise.imgUri, exercise.id)
                exercise.imgUri = downloadUir
            }

            Result.Success(exercise)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    override suspend fun delete(exercise: ExerciseDTO): Result<Unit> {
        return try {
            db.collection("exercises").document(exercise.id).delete().await()
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, e.message.toString())
        }
    }

    private suspend fun uploadPhoto(
        uriFile: Uri,
        fileName: String
    ): Uri {
        val ref = storage.getReference(fileName)
        val task = ref.putFile(uriFile)
        return generateUrlDownload(ref, task).await()
    }

    private fun generateUrlDownload(
        reference: StorageReference,
        task: StorageTask<UploadTask.TaskSnapshot>
    ): Task<Uri> {
        return task.continueWithTask { taskExecuted ->
            if (taskExecuted.isSuccessful) {
                reference.downloadUrl
            } else {
                taskExecuted.exception?.let { exception ->
                    throw exception
                }
            }
        }
    }
}