package com.ramonguimaraes.gymmate.exercises.presenter.viewModel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.ramonguimaraes.gymmate.exercises.domain.repository.ExercisesRepository
import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditExerciseViewModel(
    private val repository: ExercisesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {
    private val _saveResult = MutableLiveData<Result<Exercise>>()
    val saveResult: LiveData<Result<Exercise>> = _saveResult

    private val _deleteResult = MutableLiveData<Result<Unit>>()
    val deleteResult: LiveData<Result<Unit>> = _deleteResult

    private var imgUri: Uri? = null

    fun setImgUri(uri: Uri?) {
        imgUri = uri
    }

    fun delete(exercise: Exercise) {
        _deleteResult.postValue(Result.Loading)
        viewModelScope.launch(Dispatchers.IO) {
            _deleteResult.postValue(repository.delete(exercise))
        }
    }

    fun save(exercise: Exercise) {
        val userId = authRepository.currentUserId()
        if (userId != null) {
            _saveResult.postValue(Result.Loading)
            viewModelScope.launch(Dispatchers.IO) {
                val exerciseToSave = exercise.copy(userId = userId, imgUri = imgUri ?: Uri.EMPTY)
                val result = if (exercise.id.isNotBlank()) {
                    repository.update(exerciseToSave)
                } else {
                    repository.save(exerciseToSave)
                }

                _saveResult.postValue(result)
            }
        }
    }
}
