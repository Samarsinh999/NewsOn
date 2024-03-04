package com.samar.newsontap.appUI

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberImagePainter
import com.samar.newsontap.fetchNewsData
import com.samar.newsontap.model.Article
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import com.samar.newsontap.VModel.NewsViewModel
import java.time.Instant
import java.time.LocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen() {
    val AppBarHeight = 56.dp
    val viewModel = viewModel<NewsViewModel>()
    val articles by viewModel.articles.collectAsState()
    val errorMessage by viewModel.errorMessage.collectAsState()
    val newsList = remember { mutableStateOf<List<Article>>(emptyList()) }
    val sortedArticles = remember { mutableStateOf<List<Article>>(emptyList()) }
    val sortingOption = remember { mutableStateOf("Newest") }

    LaunchedEffect(Unit) {
        val fetchedNews = fetchNewsData()
        newsList.value = fetchedNews.articles
        val sorted = articles.sortedByDescending { Instant.parse(it.publishedAt) }
        sortedArticles.value = sorted
    }
    if (articles.isNotEmpty()) {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "NewsOn") },
//                    actions = {
//                        DropDownMenu(
//                            sortingOption = sortingOption.value,
//                            onOptionSelected = { option ->
//                                sortingOption.value = option
//                                sortArticles(option, sortedArticles.value)
//                            }
//                        )
//                    }
                )
            },
            content = {
                Column(modifier = Modifier.padding(top = AppBarHeight)) {
                  NewsList(newsList.value)
                }
            }
        )
    } else {
        if (errorMessage != null) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(errorMessage.toString())
            }
        }
    }
}

@Composable
fun DropDownMenu(sortingOption: String,
    onOptionSelected:  (String) -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier.fillMaxWidth()
            .wrapContentSize(Alignment.TopEnd)
    ) {
        IconButton(onClick = { expanded = true }) {
            Icon(
                imageVector = Icons.Default.MoreVert,
                contentDescription = "Sort"
            )
        }
        DropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            DropdownMenuItem(
                { Text("Sort by Newest", style = if (sortingOption == "Newest") TextStyle(color = Color.Blue) else TextStyle(color = Color.Black)) },
                onClick = { onOptionSelected("Newest")
                    expanded = false}
            )
            DropdownMenuItem(
                { Text("Sort by Oldest", style = if (sortingOption == "Oldest") TextStyle(color = Color.Blue) else TextStyle(color = Color.Black)) },
                onClick = { onOptionSelected("Oldest")
                expanded = false}
            )
        }
    }
}

fun sortArticles(option: String, articles: List<Article>) {
    when (option) {
        "Newest" -> articles.sortedByDescending { it.publishedAt }
        "Oldest" -> articles.sortedBy { it.publishedAt }
    }
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

@OptIn(ExperimentalCoilApi::class)
@Composable
fun NewsListItem(article: Article) {
    val launcher = rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) { }
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(article.url))
                launcher.launch(intent)
            }
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
                text = article.publishedAt,
                fontSize = 12.sp
            )
        }
    }
}


