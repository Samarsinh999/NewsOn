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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberImagePainter
import com.samar.newsontap.R
import com.samar.newsontap.fetchNewsData
import com.samar.newsontap.model.Article


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(newsList: List<Any>, param: (Any) -> Unit) {
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
            Text(
                text = "Author: ${article.author ?: "Unknown"}",
                fontSize = 14.sp,
                color = Color.Gray,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            article.imageUrl?.let { imageUrl ->
                Image(
                    painter = rememberImagePainter(data = imageUrl),
                    contentDescription = null,
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

//@Composable
//fun NewsApp() {
//    val navController = rememberNavController()
//    var article by remember { mutableStateOf(Article(url =)) } // Initialize article variable
//
//    NavHost(navController = navController, startDestination = "home") {
//        composable("home") {
//            HomeScreen(newsList = listOf()) { selectedArticle ->
//                article =
//                    selectedArticle as Article // Update article variable when an article is selected
//                navController.navigate("details/${selectedArticle.url}")
//            }
//        }
//        composable("details/{articleUrl}") { backStackEntry ->
//            val articleUrl = backStackEntry.arguments?.getString("articleUrl")
//            // Fetch article details using articleUrl from URL and pass to NewsDetailsScreen
//            // val article = fetchArticleDetails(articleUrl)
//            // NewsDetailsScreen(article)
//        }
//    }
//}


@Composable
fun loadPicture(url: String): Painter {
    return painterResource(id = R.drawable.placeholder) // Placeholder image
}