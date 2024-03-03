package com.samar.newsontap.appUI

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.*
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.samar.newsontap.model.NewsItem

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(newsList: List<NewsItem>, onItemClick: (NewsItem) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NewsOn") }
            )
        },
        content = {
            NewsList(newsList = newsList, onItemClick = onItemClick)
        }
    )
}

@Composable
fun NewsList(newsList: List<NewsItem>, onItemClick: (NewsItem) -> Unit) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        items(newsList) { newsItem ->
            NewsListItem(newsItem = newsItem, onItemClick = onItemClick)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun NewsListItem(newsItem: NewsItem, onItemClick: (NewsItem) -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { onItemClick(newsItem) })
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(text = newsItem.headline)
            // Add other details if needed
        }
    }
}
