package com.example.qheal.viewmodel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.example.qheal.model.QuoteResult
import com.example.qheal.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class QuerySearchViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    val querySearch = mutableStateOf("")
    var preQuerySearch = mutableStateOf("")
    private val _queryData = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val queryData: State<Flow<PagingData<QuoteResult>>> = _queryData

    init {
        querySearch.value = ""
    }

    fun hitQuerySearch() {
        viewModelScope.launch {
            if (querySearch.value.isNullOrEmpty()) {
                _queryData.value = homeRepository.getQuoteSearch(query = querySearch.value)
            }
        }
    }
}