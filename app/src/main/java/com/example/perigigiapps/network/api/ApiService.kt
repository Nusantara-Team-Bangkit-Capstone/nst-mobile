package com.example.perigigiapps.network.api

import com.example.perigigiapps.data.entity.User
import com.example.perigigiapps.network.response.LoginResponse
import com.example.perigigiapps.network.response.dummyResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @GET("everything?q=dental%health&sortBy=publishedAt&pageSize=10&apiKey=593e27d61fbb44d383e11ea4422f7a66")
    suspend fun getArticles(): dummyResponse

    @POST("login")
    suspend fun postLogin(
        @Body user: User
    ): LoginResponse
}