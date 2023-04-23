package com.example.qheal.data.remote

import com.example.qheal.data.remote.responce.QuoteResponse
import com.example.qheal.data.remote.responce.TagButtonResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    //https://quotable.io/quotes
    @GET("quotes?order=asc")
    suspend fun getQuotes(@Query("page") page: Int): QuoteResponse

    @GET("tags")
    suspend fun getTagContent(): List<TagButtonResponse>

    @GET("authors")
    suspend fun getAllAuthor(@Query("page") page: Int): QuoteResponse

    @GET("quotes")
    suspend fun getAuthorQuotes(
        @Query("author") author: String,
        @Query("page") page: Int
    ): QuoteResponse

    //https://api.quotable.io/search/quotes?query=albert-einstein&fields=author
    @GET("search/quotes")
    suspend fun getSearchQuotes(
        @Query("query") query: String,
        @Query("page") page: Int
    ): QuoteResponse

    //https://quotable.io/search/authors?query=einstein

    @GET("search/authors")
    suspend fun getSearchAuthors(
        @Query("query") query: String,
        @Query("page") page: Int
    ): QuoteResponse

    //https://api.quotable.io/quotes?tags=history
    @GET("quotes")
    suspend fun getTagsQuotes(@Query("tags") tags: String, @Query("page") page: Int): QuoteResponse
}


