package com.example.qheal.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.qheal.model.QuoteResult
import com.example.qheal.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AllAuthorViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {


    val authorQuerySearch = mutableStateOf("")
    val preAuthorQuerySearch = mutableStateOf("")
    private val _authorQueryState = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val authorQueryState: MutableState<Flow<PagingData<QuoteResult>>> = _authorQueryState
    private val _allAuthorList = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val allAuthorList: MutableState<Flow<PagingData<QuoteResult>>> = _allAuthorList

    init {
        authorQuerySearch.value = ""
    }

    fun hitAuthorSearch() {
        viewModelScope.launch {
            if (authorQuerySearch.value.isNotEmpty()) {
                _authorQueryState.value = homeRepository.getAuthorsSearch(authorQuerySearch.value)
            }
        }
    }

    fun fetchAllAuthor() {
        viewModelScope.launch {
            _allAuthorList.value = homeRepository.getAllAuthor().cachedIn(viewModelScope)
        }
    }
}

