package com.capstone.perigigiapps.data.repository

import androidx.lifecycle.liveData
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.network.api.ApiService
import kotlinx.coroutines.Dispatchers

class DoctorRepository(private val apiService: ApiService) {

    fun getAllDoctor(token: String) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.getAllDoctor(token)))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    companion object {
        @Volatile
        private var instance: DoctorRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): DoctorRepository =
            instance ?: synchronized(this) {
                instance ?: DoctorRepository(apiService)
            }.also { instance = it }

        private val TAG = "DoctorRepository"
    }
}