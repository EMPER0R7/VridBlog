package com.example.bloger.data

import com.google.gson.annotations.SerializedName

data class BlogPostDto(
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: BlogContentDto,
    @SerializedName("content")
    val content: BlogContentDto,
    @SerializedName("excerpt")
    val excerpt: BlogContentDto,
    @SerializedName("link")
    val link: String,
    @SerializedName("date")
    val date: String
)

data class BlogContentDto(
    @SerializedName("rendered")
    val rendered: String
)