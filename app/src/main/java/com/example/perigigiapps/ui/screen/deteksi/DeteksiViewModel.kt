package com.example.perigigiapps.ui.screen.deteksi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perigigiapps.data.repository.UserRepository
import com.example.perigigiapps.network.NetworkResult
import com.example.perigigiapps.network.response.deteksi.DeteksiResponse
import okhttp3.MultipartBody

class DeteksiViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun postPredict(imageMultipart: MultipartBody.Part):
            LiveData<NetworkResult<DeteksiResponse>> {
        return userRepository.predict(imageMultipart)
    }

    class DeteksiViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(DeteksiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return DeteksiViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}