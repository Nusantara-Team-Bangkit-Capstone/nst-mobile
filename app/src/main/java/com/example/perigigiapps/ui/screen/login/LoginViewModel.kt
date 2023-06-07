package com.example.perigigiapps.ui.screen.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perigigiapps.data.entity.User
import com.example.perigigiapps.data.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun login(user: User) = userRepository.login(user)

    class LoginViewModelFactory(private val userRepository: UserRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(LoginViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return LoginViewModel(userRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}