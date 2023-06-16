package com.capstone.perigigiapps.ui.screen.konsultasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.data.repository.DoctorRepository
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.network.response.doctor.ListDoctorResponse

class KonsultasiViewModel(private val doctorRepository: DoctorRepository) : ViewModel() {
    fun getAllDoctor(token: String): LiveData<NetworkResult<ListDoctorResponse>> {
        val authToken =
            "Bearer $token"
        return doctorRepository.getAllDoctor(authToken)
    }

    class KonsultasiViewModelFactory(private val doctorRepository: DoctorRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(KonsultasiViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return KonsultasiViewModel(doctorRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}