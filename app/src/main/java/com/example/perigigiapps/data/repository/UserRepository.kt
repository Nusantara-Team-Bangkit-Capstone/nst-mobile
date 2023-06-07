package com.example.perigigiapps.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.perigigiapps.data.entity.User
import com.example.perigigiapps.network.NetworkResult
import com.example.perigigiapps.network.api.ApiService
import kotlinx.coroutines.Dispatchers

class UserRepository(private val apiService: ApiService) {
    fun login(user: User) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.postLogin(user)))
        } catch (e: Exception) {
            Log.e(TAG, "OnFailure: $e")
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    companion object {
        @Volatile
        private var instance: UserRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): UserRepository =
            instance ?: synchronized(this) {
                instance ?: UserRepository(apiService)
            }.also { instance = it }

        private val TAG = "UserRepository"
    }
}