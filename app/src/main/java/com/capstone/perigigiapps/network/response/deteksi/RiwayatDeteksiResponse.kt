package com.capstone.perigigiapps.network.response.deteksi

import com.google.gson.annotations.SerializedName

data class RiwayatDeteksiResponse(

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class DataItem(

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
