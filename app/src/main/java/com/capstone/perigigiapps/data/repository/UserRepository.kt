package com.capstone.perigigiapps.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.capstone.perigigiapps.data.entity.User
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.network.api.ApiService
import kotlinx.coroutines.Dispatchers
import okhttp3.MultipartBody

class UserRepository(private val apiService: ApiService) {
    fun login(user: User) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.postLogin(user)))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    fun register(user: User) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.postRegister(user)))
        } catch (e: Exception) {
            Log.e(TAG, "OnFailure: $e")
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    fun predict(imageMultipart: MultipartBody.Part, id: Int?, token: String) =
        liveData(Dispatchers.IO) {
            emit(NetworkResult.Loading)
            try {
                emit(NetworkResult.Success(apiService.postPredict(imageMultipart, id, token)))
            } catch (e: Exception) {
                Log.e(TAG, "OnFailure: $e")
                emit(NetworkResult.Error(e.message.orEmpty()))
            }
        }

    fun history(token: String, id: Int?) =
        liveData(Dispatchers.IO) {
            emit(NetworkResult.Loading)
            try {
                emit(NetworkResult.Success(apiService.getHistory(id, token)))
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