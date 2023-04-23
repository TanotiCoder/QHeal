package com.example.qheal.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.qheal.data.remote.responce.TagButtonResponse
import com.example.qheal.utils.ResourceState
import com.example.qheal.model.AuthorChipNameResult
import com.example.qheal.model.QuoteResult
import com.example.qheal.repository.AuthorChipNameRepository
import com.example.qheal.repository.HomeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val homeRepository: HomeRepository,
    private val authorChipNameRepository: AuthorChipNameRepository,
) : ViewModel() {
    private val _quoteFlow = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val quoteFlow: MutableState<Flow<PagingData<QuoteResult>>> = _quoteFlow

    private val _authorChipName = mutableStateListOf(AuthorChipNameResult("", ""))
    val authorChipName: SnapshotStateList<AuthorChipNameResult> = _authorChipName

    private val _tagButton = mutableStateListOf(
        TagButtonResponse(
            name = "",
            _id = "",
            dateAdded = "",
            dateModified = "",
            quoteCount = 0,
            slug = ""
        )
    )
    val tagButton: SnapshotStateList<TagButtonResponse> = _tagButton

    init {
        homeNetworkCall()
    }

    private fun homeNetworkCall() {
        getAuthorChipName()
        getQuotes()
        getTagButton()
    }

    private fun getTagButton() {
        viewModelScope.launch {
            when (val tag = homeRepository.getTagsButton()) {
                is ResourceState.Error -> {
                    Timber.e("Something went wrong")
                }
                is ResourceState.Loading -> {}
                is ResourceState.Success -> {
                    tag.data?.forEach {
                        _tagButton.add(it)
                    }
                }
            }
        }
    }

    private fun getAuthorChipName() {
        viewModelScope.launch {
            when (val name = authorChipNameRepository.getAuthorChipName()) {
                is ResourceState.Error -> {
                    Timber.e("Something went wrong")
                }
                is ResourceState.Loading -> {}
                is ResourceState.Success -> {
                    name.data?.Results?.forEach {
                        _authorChipName.add(it)
                    }
                }
            }
        }
    }

    fun getQuotes() {
        viewModelScope.launch {
            quoteFlow.value = homeRepository.getQuotes().cachedIn(viewModelScope)
        }
    }
}

