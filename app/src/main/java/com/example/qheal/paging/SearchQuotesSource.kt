package com.example.qheal.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.qheal.data.remote.ApiService
import com.example.qheal.model.QuoteResult
import retrofit2.HttpException
import java.io.IOException

class SearchQuotesSource(private val apiService: ApiService, private val query: String) :
    PagingSource<Int, QuoteResult>() {
    override fun getRefreshKey(state: PagingState<Int, QuoteResult>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, QuoteResult> {
        return try {
            val nextPage = params.key ?: 1
            val dataQuery = apiService.getSearchQuotes(query = query, page = nextPage)

            LoadResult.Page(
                data = dataQuery.results,
                nextKey = if (dataQuery.results.isEmpty()) null else nextPage + 1,
                prevKey = if (nextPage == 1) null else nextPage - 1
            )
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}


