package com.example.qheal.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.qheal.data.remote.WikiAuthorDetailApiService
import com.example.qheal.data.remote.responce.AuthorDetailReponce
import com.example.qheal.model.Originalimage
import com.example.qheal.model.QuoteResult
import com.example.qheal.model.Thumbnail
import com.example.qheal.repository.HomeRepository
import com.example.qheal.utils.ResourceState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

//data class DetailAboutAuthor(
//    val successData: AuthorDetailReponce = AuthorDetailReponce(
//        description = "",
//        displaytitle = "",
//        extract = "",
//        lang = "",
//        originalimage = Originalimage(0, "", 0),
//        pageid = 0,
//        revision = "",
//        thumbnail = Thumbnail(height = 0, source = "", width = 0),
//        title = "",
//    ),
//    val errorData: String = "",
//    val loadingBoolean: Boolean = false
//)

@HiltViewModel
class DetailAboutAuthorViewModel @Inject constructor(private val homeRepository: HomeRepository) :
    ViewModel() {
    private val _authorDetailData = mutableStateOf(
        AuthorDetailReponce(
            description = "",
            displaytitle = "",
            extract = "",
            lang = "",
            originalimage = Originalimage(0, "", 0),
            pageid = 0,
            revision = "",
            thumbnail = Thumbnail(height = 0, source = "", width = 0),
            title = "",
        )
    )
    val authorDetailData = _authorDetailData

    private val _getAuthorQuoteData = mutableStateOf<Flow<PagingData<QuoteResult>>>(emptyFlow())
    val getAuthorQuoteData: MutableState<Flow<PagingData<QuoteResult>>> = _getAuthorQuoteData

    fun getDetailAboutAuthor(name: String) {
        viewModelScope.launch {
            when (val authorData = homeRepository.getAboutAuthor(name)) {
                is ResourceState.Error -> {
                    Timber.e("Something went wrong")
                }
                is ResourceState.Loading -> {

                }
                is ResourceState.Success -> {
                    _authorDetailData.value = authorData.data!!
                }
            }
        }
    }

    fun getQuoteByAuthor(name: String) {
        viewModelScope.launch {
            _getAuthorQuoteData.value =
                homeRepository.getAuthorQuotes(name).cachedIn(viewModelScope)
        }
    }
}