package com.example.bloger.presentation
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bloger.data.BlogPost
import com.example.bloger.data.BlogRepository

import kotlinx.coroutines.launch


class BlogViewModel : ViewModel() {
    private val repository = BlogRepository(
        RetrofitClient.api
    )

    private val _state = mutableStateOf(BlogState())
    val state: State<BlogState> = _state

    private var currentPage = 1

    init {
        loadBlogPosts()
    }

    fun loadBlogPosts() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true)

            repository.getBlogPosts(currentPage)
                .onSuccess { posts ->
                    _state.value = _state.value.copy(
                        posts = _state.value.posts + posts,
                        isLoading = false
                    )
                    currentPage++
                }
                .onFailure { error ->
                    _state.value = _state.value.copy(
                        error = error.message ?: "Unknown error occurred",
                        isLoading = false
                    )
                }
        }
    }
}

data class BlogState(
    val posts: List<BlogPost> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)