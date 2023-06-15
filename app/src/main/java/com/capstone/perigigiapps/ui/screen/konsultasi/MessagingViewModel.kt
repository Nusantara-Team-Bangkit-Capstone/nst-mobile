package com.capstone.perigigiapps.ui.screen.konsultasi

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.capstone.perigigiapps.data.entity.Chat
import com.capstone.perigigiapps.data.repository.MessagingRepository
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.network.response.messaging.DataItem

class MessagingViewModel(private val messagingRepository: MessagingRepository) : ViewModel() {
    fun postChat(token: String, chat: Chat): LiveData<NetworkResult<List<DataItem>>> {
        val authToken = "Bearer $token"
        return messagingRepository.postChat(token = authToken, chat = chat)
    }

    class MessagingViewModelFactory(private val messagingRepository: MessagingRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MessagingViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MessagingViewModel(messagingRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}