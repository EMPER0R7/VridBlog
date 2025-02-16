package com.example.bloger
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler

import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.bloger.data.BlogPost
import com.example.bloger.presentation.BlogDetailScreen
import com.example.bloger.presentation.BlogListScreen
import com.example.bloger.presentation.BlogViewModel

import com.example.bloger.ui.theme.BlogerTheme
//
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BlogerTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val currentPost = remember { mutableStateOf<BlogPost?>(null) }


                    val backDispatcher = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher
                    BackHandler(enabled = currentPost.value != null) {
                        currentPost.value = null
                    }

                    MainScreen(
                        currentPost = currentPost.value,
                        onPostClick = { post -> currentPost.value = post },
                        onBackPress = { currentPost.value = null }
                    )
                }
            }
        }
    }
}

@Composable
fun MainScreen(
    currentPost: BlogPost?,
    onPostClick: (BlogPost) -> Unit,
    onBackPress: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(0) }
    val viewModel: BlogViewModel = viewModel()

    Scaffold(
        bottomBar = {
            if (currentPost == null) {  // Only show bottom bar when not in detail view
                NavigationBar {
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Home, contentDescription = "Home") },
                        label = { Text("Home") },
                        selected = selectedTab == 0,
                        onClick = { selectedTab = 0 }
                    )
                    NavigationBarItem(
                        icon = { Icon(Icons.Default.Bookmark, contentDescription = "Saved") },
                        label = { Text("Saved") },
                        selected = selectedTab == 1,
                        onClick = { selectedTab = 1 }
                    )
                }
            }
        }
    ) { paddingValues ->
        Box(modifier = Modifier.padding(paddingValues)) {
            if (currentPost == null) {
                when (selectedTab) {
                    0 -> BlogListScreen(
                        viewModel = viewModel,
                        onPostClick = onPostClick
                    )
                    1 -> SavedPostsScreen()
                }
            } else {
                BlogDetailScreen(
                    post = currentPost,
                    onBackPressed = onBackPress
                )
            }
        }
    }
}

@Composable
fun SavedPostsScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = Icons.Default.Bookmark,
                contentDescription = null,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "No saved posts yet",
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}