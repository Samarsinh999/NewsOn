package com.samar.newsontap.appUI

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.samar.newsontap.R
import com.samar.newsontap.fetchNewsData
import com.samar.newsontap.model.Article


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val newsList = remember { mutableStateOf<List<Article>>(emptyList()) }

    LaunchedEffect(Unit) {
        val fetchedNews = fetchNewsData()
        newsList.value = fetchedNews.articles
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "NewsOn") }
            )
        },
        content = {
            NewsList(newsList.value)
        }
    )
}

@Composable
fun NewsList(newsList: List<Article>) {
    LazyColumn(
        modifier = Modifier.fillMaxSize()
    ) {
        items(newsList) { article ->
            NewsListItem(article)
        }
    }
}

@Composable
fun NewsListItem(article: Article) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = { /* Handle item click */ })
            .padding(8.dp),
//        elevation = 4.dp
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = article.title,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            article.imageUrl?.let { imageUrl ->
                Image(
                    painter = loadPicture(url = imageUrl),
                    contentDescription = "News Image",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                )
            }
            Text(
                text = article.description ?: "",
                fontSize = 14.sp,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(
                text = article.publishedAt,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun loadPicture(url: String): Painter {
    return painterResource(id = R.drawable.placeholder) // Placeholder image
}