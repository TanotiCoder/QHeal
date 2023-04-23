package com.example.qheal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.qheal.paging.TagsQuotesSource
import com.example.qheal.screens.destinations.DetailAboutAuthorScreenDestination
import com.example.qheal.shareComposed.PostUi
import com.example.qheal.shareComposed.TagBackButton
import com.example.qheal.utils.Utils
import com.example.qheal.viewmodel.TagQuoteViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun TagsQuotesScreen(
    tag: String,
    tagQuoteViewModel: TagQuoteViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val tagQuotesData = tagQuoteViewModel.allTagsData.value.collectAsLazyPagingItems()

    LaunchedEffect(key1 = tag) {
        tagQuoteViewModel.fetchTagsQuotes(tag)
    }
    Column(Modifier.fillMaxSize()) {
        Row(
            Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(64.dp),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TagBackButton(tagsName = tag, modifier = Modifier) {
                navigator.navigateUp()
            }
        }
        when (tagQuotesData.loadState.refresh) {
            is LoadState.NotLoading -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(tagQuotesData) { item ->
                        PostUi(
                            authorName = item?.author ?: "null",
                            postId = item?._id ?: "null",
                            content = item!!.content,
                            postImg = Utils.MEDIA_URL + (1..100).random(),
                            isSpark = false,
                            isBookmark = false,
                            sparkOnClick = { /*TODO*/ },
                            authorSlug = item.authorSlug,
                            bookmarkOnClick = {},
                            aboutAuthorOnClick = {
                                navigator.navigate(DetailAboutAuthorScreenDestination(item.author))
                            }
                        )
                    }
                }
            }
            LoadState.Loading -> {
                LoadingCircularBox()
            }
            is LoadState.Error -> {
                ErrorScreenBox()
            }
        }
    }
}