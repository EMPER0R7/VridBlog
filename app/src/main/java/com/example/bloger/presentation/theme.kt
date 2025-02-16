package com.example.bloger.presentation

// Theme.kt


import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import com.example.bloger.data.BlogPost
import com.example.bloger.util.formatToReadableDate
import com.example.bloger.util.removeHtmlTags
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.accompanist.swiperefresh.SwipeRefresh

val md_theme_light_primary = Color(0xFF006C4C)
val md_theme_light_surface = Color(0xFFFBFDF9)
val md_theme_light_background = Color(0xFFFBFDF9)
val md_theme_light_surfaceVariant = Color(0xFFDCE5DC)

val LightColors = lightColorScheme(
    primary = md_theme_light_primary,
    surface = md_theme_light_surface,
    background = md_theme_light_background,
    surfaceVariant = md_theme_light_surfaceVariant
)
//
//// BlogListScreen.kt
//@Composable
//fun BlogListScreen(
//    viewModel: BlogViewModel,
//    onPostClick: (BlogPost) -> Unit
//) {
//    val state = viewModel.state.value
//    val listState = rememberLazyListState()
//
//    Box(modifier = Modifier.fillMaxSize()) {
//        LazyColumn(
//            state = listState,
//            contentPadding = PaddingValues(16.dp),
//            verticalArrangement = Arrangement.spacedBy(16.dp)
//        ) {
//            items(state.posts) { post ->
//                BlogPostCard(
//                    post = post,
//                    onClick = { onPostClick(post) }
//                )
//            }
//
//            item {
//                if (state.isLoading) {
//                    CircularProgressIndicator(
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(16.dp)
//                            .wrapContentWidth(Alignment.CenterHorizontally)
//                    )
//                }
//            }
//        }
//
//
//        SwipeRefresh(
//            state = rememberSwipeRefreshState(state.isLoading),
//            onRefresh = { viewModel.loadBlogPosts() }
//        ) {
//            LazyColumn(
//                state = listState,
//                contentPadding = PaddingValues(16.dp),
//                verticalArrangement = Arrangement.spacedBy(16.dp)
//            ) {
//                items(state.posts) { post ->
//                    BlogPostCard(
//                        post = post,
//                        onClick = { onPostClick(post) }
//                    )
//                }
//
//                item {
//                    if (state.isLoading) {
//                        CircularProgressIndicator(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .padding(16.dp)
//                                .wrapContentWidth(Alignment.CenterHorizontally)
//                        )
//                    }
//                }
//            }
//        }
//
//
//
//        state.error?.let { error ->
//            ErrorMessage(
//                message = error,
//                modifier = Modifier.align(Alignment.Center)
//            )
//        }
//    }
//
//    // Implement infinite scroll
//    LaunchedEffect(listState) {
//        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
//            .collect { lastVisibleIndex ->
//                if (lastVisibleIndex != null && lastVisibleIndex >= state.posts.size - 3) {
//                    viewModel.loadBlogPosts()
//                }
//            }
//    }
//}

@Composable
fun BlogPostCard(
    post: BlogPost,
    onClick: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface,
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 2.dp
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Text(
                text = post.getFormattedTitle(),
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = post.date.formatToReadableDate(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = post.excerpt.removeHtmlTags(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BlogDetailScreen(
    post: BlogPost,
    onBackPressed: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = post.getFormattedTitle(),
                        style = MaterialTheme.typography.titleLarge,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackPressed) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                    titleContentColor = MaterialTheme.colorScheme.onSurface,
                )
            )
        }
    ) { paddingValues ->
        AndroidView(
            factory = { context ->
                WebView(context).apply {
                    settings.javaScriptEnabled = true
                    webViewClient = WebViewClient()
                    loadUrl(post.link)
                }
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        )
    }
}

//@Composable
//fun ErrorMessage(
//    message: String,
//    modifier: Modifier = Modifier
//) {
//    Column(
//        modifier = modifier
//            .fillMaxWidth()
//            .padding(16.dp),
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Icon(
//            imageVector = Icons.Default.Warning,
//            contentDescription = null,
//            tint = MaterialTheme.colorScheme.error,
//            modifier = Modifier.size(48.dp)
//        )
//
//        Spacer(modifier = Modifier.height(8.dp))
//
//        Text(
//            text = message,
//            style = MaterialTheme.typography.bodyLarge,
//            color = MaterialTheme.colorScheme.error,
//            textAlign = TextAlign.Center
//        )
//    }
//}