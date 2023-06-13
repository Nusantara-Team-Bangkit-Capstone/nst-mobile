package com.example.perigigiapps.ui.screen.konsultasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class KonsultasiViewModel : ViewModel() {
    private val _text = MutableLiveData<String>().apply {
        value = "Still work in progress"
    }
    val text: LiveData<String> = _text
}