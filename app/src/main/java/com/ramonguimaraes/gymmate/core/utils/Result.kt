package com.ramonguimaraes.gymmate.core.utils

sealed class Result<out T> {

    data class Success<out T>(val data: T) : Result<T>()
    data class Error(
        val exception: Exception,
        val errorMessage: String,
    ) : Result<Nothing>()
    data object Loading : Result<Nothing>()

    fun <ToMap> mapResultSuccess(getData: (T) -> ToMap): Result<ToMap> {
        return when (this) {
            is Success -> {
                Success(getData(data))
            }
            is Loading -> {
                this
            }
            is Error -> {
                this
            }
        }
    }
}
