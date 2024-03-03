package com.samar.newsontap

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

suspend fun fetchNewsData(): String {
    return withContext(Dispatchers.IO) {
        val url = URL("https://candidate-test-data-moengage.s3.amazonaws.com/Android/news-api-feed/staticResponse.json")
        val urlConnection = url.openConnection() as HttpURLConnection
        try {
            val inputStream = urlConnection.inputStream
            inputStream.bufferedReader().use { it.readText() }
        } finally {
            urlConnection.disconnect()
        }
    }
}
