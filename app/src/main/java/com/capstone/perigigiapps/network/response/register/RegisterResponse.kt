package com.capstone.perigigiapps.network.response.register

import com.google.gson.annotations.SerializedName

data class RegisterResponse(

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("status")
    val status: Boolean? = null
)

data class Data(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("jenis_kelamin")
    val jenisKelamin: String? = null,

    @field:SerializedName("email")
    val email: String? = null,

    @field:SerializedName("no_telepon")
    val no_telepon: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
