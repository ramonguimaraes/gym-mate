package com.ramonguimaraes.gymmate.exercises.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository
import com.ramonguimaraes.gymmate.exercises.domain.model.Exercise
import com.ramonguimaraes.gymmate.exercises.domain.repository.ExercisesRepository
import com.ramonguimaraes.gymmate.core.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExercisesViewModel(
    private val repository: ExercisesRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _exercises = MutableLiveData<Result<List<Exercise>>>()
    val exercises: LiveData<Result<List<Exercise>>> = _exercises

    fun getAll(workoutId: String) {
        val userId = authRepository.currentUserId()
        viewModelScope.launch(Dispatchers.IO) {
            if (userId != null) {
                _exercises.postValue(repository.getAll(workoutId, userId))
            }
        }
    }
}