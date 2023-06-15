package com.capstone.perigigiapps.network.response.messaging

import com.google.gson.annotations.SerializedName

data class DataItemResponse(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("receiver")
    val receiver: String? = null,

    @field:SerializedName("sender")
    val sender: String? = null,

    @field:SerializedName("id")
    val id: Int,

    @field:SerializedName("message")
    val message: String? = null,
)