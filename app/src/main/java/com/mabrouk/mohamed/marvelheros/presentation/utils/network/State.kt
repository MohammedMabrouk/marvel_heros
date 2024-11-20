package com.mabrouk.mohamed.marvelheros.presentation.utils.network

sealed class State<out T : Any> {
    data object Loading : State<Nothing>()
    data object Empty : State<Nothing>()
    data class Success<out T : Any>(val data: T) : State<T>()
    data class Error(val errorMessage: String) : State<Nothing>()
}