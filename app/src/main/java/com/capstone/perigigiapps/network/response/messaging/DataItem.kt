package com.capstone.perigigiapps.network.response.messaging

data class DataItem(
    val createdAt: String? = null,
    val receiver: String? = null,
    val id: Int,
    val message: String? = null,
    val receiverName: String,
)