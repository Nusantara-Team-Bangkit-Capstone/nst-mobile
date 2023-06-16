package com.capstone.perigigiapps.data.repository

import androidx.lifecycle.liveData
import com.capstone.perigigiapps.data.entity.Chat
import com.capstone.perigigiapps.network.NetworkResult
import com.capstone.perigigiapps.network.api.ApiService
import com.capstone.perigigiapps.network.request.messaging.ChatRequest
import com.capstone.perigigiapps.network.response.messaging.DataItem
import kotlinx.coroutines.Dispatchers

class MessagingRepository(private val apiService: ApiService) {

//    fun postChat(token: String, id: Int) = liveData(Dispatchers.IO) {
//        emit(NetworkResult.Loading)
//        try {
//            emit(NetworkResult.Success(apiService.postChat(token = token, id_user = id)))
//        } catch (e: Exception) {
//            emit(NetworkResult.Error(e.message.orEmpty()))
//        }
//    }

    fun postChat(token: String, chat: Chat) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            apiService.postChat(
                token = token,
                chatRequest = ChatRequest(chat.sender, chat.receiver, chat.message)
            )
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
        emit(NetworkResult.Loading)
        try {
            val listChat = apiService.getChat(token = token, chat.sender).data
            val incomingMessageList = arrayListOf<DataItem>()
            val outgoingMessageList = arrayListOf<DataItem>()
            for (data in listChat) {
                if (data.sender == chat.sender && chat.receiver == data.receiver) {
                    outgoingMessageList.add(
                        DataItem(
                            createdAt = data.createdAt,
                            receiver = data.sender,
                            message = data.message,
                            id = data.id,
                            receiverName = chat.senderName
                        )
                    )
                }

                if (data.sender == chat.receiver && data.receiver == chat.sender) {
                    incomingMessageList.add(
                        DataItem(
                            createdAt = data.createdAt,
                            receiver = data.receiver,
                            message = data.message,
                            id = data.id,
                            receiverName = chat.receiverName
                        )
                    )
                }
            }
            val allMessage = outgoingMessageList + incomingMessageList
            allMessage.sortedByDescending { it.id }
            emit(NetworkResult.Success(allMessage))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    fun getChat2(token: String, chat: Chat) = liveData(Dispatchers.IO) {
        emit(NetworkResult.Loading)
        try {
            emit(NetworkResult.Success(apiService.getChat(token = token, chat.sender)))
        } catch (e: Exception) {
            emit(NetworkResult.Error(e.message.orEmpty()))
        }
    }

    companion object {
        @Volatile
        private var instance: MessagingRepository? = null
        fun getInstance(
            apiService: ApiService,
        ): MessagingRepository =
            instance ?: synchronized(this) {
                instance ?: MessagingRepository(apiService)
            }.also { instance = it }

        private val TAG = "DoctorRepository"
    }
}