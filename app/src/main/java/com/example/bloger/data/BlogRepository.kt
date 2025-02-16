package com.example.bloger.data

class BlogRepository(
    private val api: BlogApi
) {
    suspend fun getBlogPosts(page: Int): Result<List<BlogPost>> = try {
        val posts = api.getPosts(page = page).map { dto ->
            BlogPost(
                id = dto.id,
                title = dto.title.rendered,
                content = dto.content.rendered,
                excerpt = dto.excerpt.rendered,
                link = dto.link,
                date = dto.date
            )
        }
        Result.success(posts)
    } catch (e: Exception) {
        Result.failure(e)
    }
}