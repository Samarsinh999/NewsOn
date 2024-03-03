package com.samar.newsontap

import com.google.gson.Gson
import com.samar.newsontap.model.NewsResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

suspend fun fetchNewsData(): NewsResponse {
    return withContext(Dispatchers.IO) {
        val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream = urlConnection.inputStream
            val response = inputStream.bufferedReader().use { it.readText() }
            Gson().fromJson(response, NewsResponse::class.java)
        } finally {
            urlConnection.disconnect()
        }
    }
}
