package com.example.perigigiapps.network

sealed class NetworkResult<out R> private constructor() {
    data class Success<out T>(val data: T) : NetworkResult<T>()
    data class Error(val error: String) : NetworkResult<Nothing>()
    object Loading : NetworkResult<Nothing>()
}