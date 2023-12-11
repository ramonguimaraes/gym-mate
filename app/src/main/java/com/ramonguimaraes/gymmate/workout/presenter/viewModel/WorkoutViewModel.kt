package com.ramonguimaraes.gymmate.workout.presenter.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.ramonguimaraes.gymmate.authentication.domain.repository.AuthRepository
import com.ramonguimaraes.gymmate.core.utils.Result
import com.ramonguimaraes.gymmate.workout.domain.model.Workout
import com.ramonguimaraes.gymmate.workout.domain.repository.WorkoutRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class WorkoutViewModel(
    private val repository: WorkoutRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _workouts = MutableLiveData<Result<List<Workout>>>()
    val workout: LiveData<Result<List<Workout>>> = _workouts

    private val _saveResult = MutableLiveData<Result<Workout>>()
    val saveResult: LiveData<Result<Workout>> = _saveResult

    fun delete(workout: Workout) {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.delete(workout)

            if (result is Result.Success) {
                updateList(workout, true)
            }
        }
    }

    fun getAll() {
        val userId = authRepository.currentUserId()
        if (userId != null) {
            viewModelScope.launch(Dispatchers.IO) {
                _workouts.postValue(repository.getAll(userId))
            }
        }
    }

    fun save(workout: Workout?) {
        val userId = authRepository.currentUserId()
        if (userId != null && workout != null) {
            viewModelScope.launch(Dispatchers.IO) {
                val result = if (workout.id.isNotBlank()) {
                    repository.update(workout)
                } else {
                    repository.save(workout.copy(userId = userId))
                }

                if (result is Result.Success) {
                    updateList(result.data)
                }
                _saveResult.postValue(result)
            }
        }
    }

    private fun updateList(workout: Workout, remove: Boolean = false) {
        val oldList = (_workouts.value as? Result.Success)?.data.orEmpty() as MutableList
        val newList = if (remove) {
            oldList.removeIf { it.id == workout.id }
            oldList
        } else {
            val index = oldList.indexOfFirst { it.id == workout.id }
            if (index != -1) {
                oldList[index] = oldList[index].copy(
                    name = workout.name,
                    description = workout.description
                )
                oldList
            } else {
                oldList + listOf(workout)
            }

        }
        _workouts.postValue(Result.Success(newList))
    }
}