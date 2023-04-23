package com.example.qheal.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.qheal.utils.ResourceState
import com.example.qheal.data.remote.ApiService
import com.example.qheal.data.remote.WikiAuthorDetailApiService
import com.example.qheal.data.remote.responce.AuthorDetailReponce
import com.example.qheal.data.remote.responce.TagButtonResponse
import com.example.qheal.model.QuoteResult
import com.example.qheal.paging.*
import kotlinx.coroutines.flow.Flow
import retrofit2.HttpException
import javax.inject.Inject

class HomeRepository @Inject constructor(
    private val apiService: ApiService,
    private val wikiAuthorDetailApiService: WikiAuthorDetailApiService
) {
    fun getQuotes(): Flow<PagingData<QuoteResult>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                QuotesSource(apiService = apiService)
            }
        ).flow
    }

    suspend fun getTagsButton(): ResourceState<List<TagButtonResponse>> {
        return try {
            ResourceState.Success(apiService.getTagContent())
        } catch (e: Exception) {
            ResourceState.Error(e.localizedMessage!!)
        } catch (e: HttpException) {
            ResourceState.Error(e.localizedMessage!!)
        }
    }

    fun getAllAuthor(): Flow<PagingData<QuoteResult>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                AllAuthorSource(apiService)
            }
        ).flow
    }

    suspend fun getAboutAuthor(name: String): ResourceState<AuthorDetailReponce> {
        return try {
            ResourceState.Success(wikiAuthorDetailApiService.getAuthorDetail(name))
        } catch (e: Exception) {
            ResourceState.Error(e.localizedMessage!!)
        } catch (e: HttpException) {
            ResourceState.Error(e.localizedMessage!!)
        }
    }

    fun getAuthorQuotes(authorName: String): Flow<PagingData<QuoteResult>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                GetAuthorsQuoteSource(apiService, authorName)
            }
        ).flow
    }

    fun getQuoteSearch(query: String): Flow<PagingData<QuoteResult>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchQuotesSource(apiService, query)
            }
        ).flow
    }

    fun getAuthorsSearch(query: String): Flow<PagingData<QuoteResult>> {
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                SearchAuthorsSource(apiService, query)
            }
        ).flow
    }
    fun getTagQuote(tag:String):Flow<PagingData<QuoteResult>>{
        return Pager(
            config = PagingConfig(20, enablePlaceholders = false),
            pagingSourceFactory = {
                TagsQuotesSource(apiService, tag)
            }
        ).flow
    }
}

