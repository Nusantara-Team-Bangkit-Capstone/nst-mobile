package com.capstone.perigigiapps.di

import com.capstone.perigigiapps.data.repository.ArticleRepository
import com.capstone.perigigiapps.data.repository.DoctorRepository
import com.capstone.perigigiapps.data.repository.MessagingRepository
import com.capstone.perigigiapps.data.repository.UserRepository
import com.capstone.perigigiapps.network.api.ApiConfig
import com.capstone.perigigiapps.network.api.dummyApiConfig

object Injection {
    fun provideArticleRepository(): ArticleRepository {
        val apiService = dummyApiConfig.getApiService()
        return ArticleRepository.getInstance(apiService)
    }

    fun provideUserRepository(): UserRepository {
        val apiService = ApiConfig.getApiService()
        return UserRepository.getInstance(apiService)
    }

    fun provideDoctorRepository(): DoctorRepository {
        val apiService = ApiConfig.getApiService()
        return DoctorRepository.getInstance(apiService)
    }

    fun provideMessagingRepository(): MessagingRepository {
        val apiService = ApiConfig.getApiService()
        return MessagingRepository.getInstance(apiService)
    }
}