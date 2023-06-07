package com.example.perigigiapps.ui.screen.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.perigigiapps.data.repository.ArticleRepository
import com.example.perigigiapps.network.NetworkResult
import com.example.perigigiapps.network.response.dummyResponse

class HomeViewModel(private val articleRepository: ArticleRepository) : ViewModel() {

    fun getAllArticles(): LiveData<NetworkResult<dummyResponse>> {
        return articleRepository.getArticles()
    }

    class HomeViewModelFactory(private val articleRepository: ArticleRepository) :
        ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return HomeViewModel(articleRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}