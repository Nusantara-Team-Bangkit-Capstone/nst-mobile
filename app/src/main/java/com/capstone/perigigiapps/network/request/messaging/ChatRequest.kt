package com.capstone.perigigiapps.network.request.messaging

data class ChatRequest(
    val sender: String,
    val receiver: String,
    val message: String,
)
