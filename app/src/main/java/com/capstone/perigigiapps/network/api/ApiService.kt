package com.capstone.perigigiapps.network.api

import com.capstone.perigigiapps.data.entity.User
import com.capstone.perigigiapps.network.response.deteksi.HasilDeteksiResponse
import com.capstone.perigigiapps.network.response.deteksi.RiwayatDeteksiResponse
import com.capstone.perigigiapps.network.response.doctor.ListDoctorResponse
import com.capstone.perigigiapps.network.response.dummyResponse
import com.capstone.perigigiapps.network.response.login.LoginResponse
import com.capstone.perigigiapps.network.response.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

interface ApiService {
    @GET("everything?q=dental%health&sortBy=publishedAt&pageSize=10&apiKey=593e27d61fbb44d383e11ea4422f7a66")
    suspend fun getArticles(): dummyResponse

    @GET("everything?q=dental&sortBy=publishedAt&pageSize=10&apiKey=593e27d61fbb44d383e11ea4422f7a66")
    suspend fun getArticles2(): dummyResponse

    @POST("login")
    suspend fun postLogin(
        @Body user: User
    ): LoginResponse

    @POST("user")
    suspend fun postRegister(
        @Body user: User
    ): RegisterResponse

    @Multipart
    @POST("predict/{id}")
    suspend fun postPredict(
        @Part image: MultipartBody.Part,
        @Path("id") id: Int?,
        @Header("Authorization") token: String,
    ): HasilDeteksiResponse


    @GET("history/{id}")
    suspend fun getHistory(
        @Path("id") id: Int?,
        @Header("Authorization") token: String
    ): RiwayatDeteksiResponse

    @GET("doctor")
    suspend fun getAllDoctor(
        @Header("Authorization") token: String
    ): ListDoctorResponse
}