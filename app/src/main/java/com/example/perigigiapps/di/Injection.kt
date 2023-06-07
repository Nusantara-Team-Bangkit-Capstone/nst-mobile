package com.example.perigigiapps.di

import com.example.perigigiapps.data.repository.ArticleRepository
import com.example.perigigiapps.data.repository.UserRepository
import com.example.perigigiapps.network.api.ApiConfig
import com.example.perigigiapps.network.api.dummyApiConfig

object Injection {
    fun provideArticleRepository(): ArticleRepository {
        val apiService = dummyApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }
}