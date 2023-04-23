package com.example.qheal.model

data class QuoteResult(
    val _id: String,
    val author: String,
    val authorSlug: String,
    val content: String,
    val dateAdded: String,
    val dateModified: String,
    val length: Int,
    val tags: List<String>,
    val bio: String,
    val description: String,
    val link: String,
    val name: String,
    val quoteCount: Int,
    val slug: String
)


