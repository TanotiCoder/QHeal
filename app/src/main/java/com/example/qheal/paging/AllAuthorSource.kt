package com.example.qheal.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.qheal.data.remote.ApiService
import com.example.qheal.model.QuoteResult
import retrofit2.HttpException

class AllAuthorSource(private val apiService: ApiService) : PagingSource<Int, QuoteResult>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteResult> {
        return try {
            val nextPage = params.key ?: 1
            val data = apiService.getAllAuthor(nextPage)
            LoadResult.Page(
                data = data.results,
                nextKey = if (data.results.isEmpty()) null else nextPage + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1
            )
        } catch (e: HttpException) {
            LoadResult.Error(e)
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}

