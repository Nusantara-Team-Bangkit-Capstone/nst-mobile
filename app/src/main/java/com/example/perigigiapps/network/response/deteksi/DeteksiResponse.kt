package com.example.perigigiapps.network.response.deteksi

import com.google.gson.annotations.SerializedName

data class DeteksiResponse(

    @field:SerializedName("data")
    val data: String? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)
