package com.example.perigigiapps.network.api

import com.example.perigigiapps.data.entity.User
import com.example.perigigiapps.network.response.deteksi.DeteksiResponse
import com.example.perigigiapps.network.response.dummyResponse
import com.example.perigigiapps.network.response.login.LoginResponse
import com.example.perigigiapps.network.response.register.RegisterResponse
import okhttp3.MultipartBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {
    @GET("everything?q=dental%health&sortBy=publishedAt&pageSize=10&apiKey=593e27d61fbb44d383e11ea4422f7a66")
    suspend fun getArticles(): dummyResponse

    @POST("login")
    suspend fun postLogin(
        @Body user: User
    ): LoginResponse

    @POST("user")
    suspend fun postRegister(
        @Body user: User
    ): RegisterResponse

    @Multipart
    @POST("predict")
    suspend fun postPredict(
        @Part file: MultipartBody.Part,
    ): DeteksiResponse
}