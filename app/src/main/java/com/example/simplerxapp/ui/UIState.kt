package com.example.simplerxapp.ui

sealed class UIState<out I> {

    data object Idle: UIState<Unit>()
    data object Loading: UIState<Unit>()
    data class Error(val errorMessage: String): UIState<Unit>()
    data class Success<T>(val data: T): UIState<T>()
    data object SuccessNoData: UIState<Unit>()

    val isSuccess: Boolean
        get() = this is Success || this is SuccessNoData

    val isError: Boolean
        get() = this is Error

    val isLoading: Boolean
        get() = this is Loading

    inline fun <reified T> getOrNull(): T? {
        return when {
            this is Success -> this.data as? T
            else -> null
        }
    }
}