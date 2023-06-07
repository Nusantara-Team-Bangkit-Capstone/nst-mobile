package com.example.perigigiapps.network.response

import com.google.gson.annotations.SerializedName

data class LoginResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Data(

    @field:SerializedName("token")
    val token: String? = null
)
