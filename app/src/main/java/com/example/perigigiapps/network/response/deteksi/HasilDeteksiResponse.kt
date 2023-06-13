package com.example.perigigiapps.network.response.deteksi

import com.google.gson.annotations.SerializedName

data class HasilDeteksiResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Data(

    @field:SerializedName("scanning_result")
    val scanningResult: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("id_user")
    val idUser: Int? = null,

    @field:SerializedName("tooth_img")
    val toothImg: String? = null,

    @field:SerializedName("scanning_date")
    val scanningDate: String? = null
)
