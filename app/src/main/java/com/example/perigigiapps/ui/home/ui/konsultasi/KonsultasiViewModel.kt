package com.example.perigigiapps.ui.home.ui.konsultasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KonsultasiViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "This is Konsultasi Fragment"
    }
    val text: LiveData<String> = _text
}