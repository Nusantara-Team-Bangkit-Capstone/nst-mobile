package com.capstone.perigigiapps.ui.screen.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.data.entity.User
import com.capstone.perigigiapps.data.repository.UserRepository


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun register(user: User) = userRepository.register(user)

    class RegisterViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(RegisterViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return RegisterViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}