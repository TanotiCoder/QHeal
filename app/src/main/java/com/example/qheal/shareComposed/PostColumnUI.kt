package com.example.qheal.shareComposed

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.itemsIndexed
import com.example.qheal.data.remote.responce.TagButtonResponse
import com.example.qheal.model.AuthorChipNameResult
import com.example.qheal.model.QuoteResult
import com.example.qheal.screens.LoadingCircularBox
import com.example.qheal.screens.destinations.AllAuthorScreenDestination
import com.example.qheal.screens.destinations.DetailAboutAuthorScreenDestination
import com.example.qheal.screens.destinations.TagsQuotesScreenDestination
import com.example.qheal.utils.Utils.MEDIA_URL
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Composable
fun PostCardColum(
    pagingData: LazyPagingItems<QuoteResult>,
    authorNameData: List<AuthorChipNameResult>,
    tagButton: List<TagButtonResponse>,
    paddingValues: PaddingValues,
    navigator: DestinationsNavigator,
) {

    when (pagingData.loadState.refresh) {
        is LoadState.NotLoading -> {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                contentPadding = PaddingValues(vertical = 16.dp),
            ) {
                item {
                    LazyRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        items(authorNameData) { authorNameData ->
                            if (authorNameData.slug.isNotEmpty() || authorNameData.name.isNotEmpty()) {
                                AuthorNameChips(
                                    authorSlug = authorNameData.slug,
                                    chipOnClick = {
                                        navigator.navigate(
                                            DetailAboutAuthorScreenDestination(
                                                authorNameData.name
                                            )
                                        )
                                    },
                                    authorName = authorNameData.name
                                )
                            }
                        }

                        item {
                            AuthorChipsArrow {
                                navigator.navigate(AllAuthorScreenDestination)
                            }
                        }
                    }
                }

                itemsIndexed(pagingData) { index, item ->
                    if (index == 3) {
                        Column(Modifier.fillMaxWidth()) {
                            Text(
                                text = "Hashtag",
                                style = MaterialTheme.typography.titleLarge,
                                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
                            )
                            TagsFlowRow(
                                tagData = tagButton,
                                modifier = Modifier,
                                tagQuotesOnClick = {
                                    navigator.navigate(TagsQuotesScreenDestination(tag = it))
                                }
                            )
                        }
                    }
                    PostUi(
                        authorName = item?.author ?: "null",
                        postId = item?._id ?: "null",
                        content = item!!.content,
                        postImg = MEDIA_URL + (1..1000).random(),
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
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = "Error", color = Color.Red)
            }
        }
    }
}