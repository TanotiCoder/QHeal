package com.example.qheal.data.remote.responce

import com.example.qheal.model.QuoteResult

data class QuoteResponse(
    val count: Int,
    val lastItemIndex: Int,
    val page: Int,
    val results: List<QuoteResult>,
    val totalCount: Int,
    val totalPages: Int,
)

