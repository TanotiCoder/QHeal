package com.example.qheal.viewmodel

import androidx.compose.runtime.MutableState
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
class TagQuoteViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    private val _allTagsData = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val allTagsData: MutableState<Flow<PagingData<QuoteResult>>> = _allTagsData

    fun fetchTagsQuotes(tag:String){
        viewModelScope.launch {
            _allTagsData.value = homeRepository.getTagQuote(tag = tag)
        }
    }
}