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
            "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6MTUsIm5hbWUiOiJkcmcuIEZhdHJpY2lhIE1vcmF5IiwiZW1haWwiOiJmYXJoYW5AZ21haWwuY29tIiwiZm90byI6Imh0dHBzOi8vc3RvcmFnZS5nb29nbGVhcGlzLmNvbS9uc3QtYnVja2V0LWRldi1lbnYvZG9jdG9yLXByb2ZpbGUtVGh1IEp1biAxNSAyMDIzIDEwOjAwOjA0IEdNVCswMDAwIChDb29yZGluYXRlZCBVbml2ZXJzYWwgVGltZSkiLCJpYXQiOjE2ODY4NDA5MzcsImV4cCI6MTY4Njg0NDUzN30.OYLVgUdKvwXWAlJa0QEyM4ztXhMr2xwdivMbGTTcoOI"
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