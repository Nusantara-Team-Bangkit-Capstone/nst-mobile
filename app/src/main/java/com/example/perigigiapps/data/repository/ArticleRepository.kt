package com.example.perigigiapps.data.repository

import android.util.Log
import androidx.lifecycle.liveData
import com.example.perigigiapps.network.NetworkResult
import com.example.perigigiapps.network.api.ApiService
import kotlinx.coroutines.Dispatchers


class ArticleRepository(private val apiService: ApiService) {
    fun getArticles() = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.getArticles()))
        } catch (e: Exception) {
            Log.e(TAG, "OnFailure: $e")
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    companion object {
        @Volatile
        private var instance: ArticleRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): ArticleRepository =
            instance ?: synchronized(this) {
                instance ?: ArticleRepository(apiService)
            }.also { instance = it }

        private val TAG = "UserRepository"
    }
}