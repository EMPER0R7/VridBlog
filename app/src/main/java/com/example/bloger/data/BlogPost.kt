package com.example.bloger.data

data class BlogPost(
    val id: Int,
    val title: String,
    val content: String,
    val excerpt: String,
    val link: String,
    val date: String
) {
    fun getFormattedTitle(): String {
        return title.replace("[^\\w\\s]".toRegex(), "")
    }
}