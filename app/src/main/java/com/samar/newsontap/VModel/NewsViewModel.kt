package com.samar.newsontap.VModel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.samar.newsontap.fetchNewsData
import com.samar.newsontap.model.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.net.ConnectException
import java.net.UnknownHostException

class NewsViewModel : ViewModel() {
    private val _articles = MutableStateFlow<List<Article>>(listOf())
    val articles: StateFlow<List<Article>> = _articles

    private val _loading = MutableStateFlow(true)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    init {
        fetchArticles()
    }

    fun fetchArticles() {
        viewModelScope.launch {
            try {
                val responseData = fetchNewsData()
                _articles.value = responseData.articles // Update the articles state flow
                Log.d("article",_articles.value.toString())
            } catch (e: Exception) {
                handleApiError(e)
            }
        }
    }



    private fun handleApiError(exception: Exception) {
        _loading.value = false
        when (exception) {
            is UnknownHostException, is ConnectException -> {
                _errorMessage.value = "No internet connection"
            }

            else -> {
                _errorMessage.value = "An error occurred"
            }
        }
    }
}