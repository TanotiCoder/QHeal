package com.example.qheal.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.qheal.shareComposed.*
import com.example.qheal.utils.Utils
import com.example.qheal.viewmodel.DetailAboutAuthorViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@Destination
@Composable
fun DetailAboutAuthorScreen(
    name: String,
    detailAboutAuthorViewModel: DetailAboutAuthorViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    LaunchedEffect(key1 = name) {
        detailAboutAuthorViewModel.getDetailAboutAuthor(name)
        detailAboutAuthorViewModel.getQuoteByAuthor(name)
    }
    val dataAuthor = detailAboutAuthorViewModel.authorDetailData.value
    val pagingData = detailAboutAuthorViewModel.getAuthorQuoteData.value.collectAsLazyPagingItems()
    LazyColumn(
        Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Column(
                Modifier
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                DetailScreenBgImg(
                    url = dataAuthor.originalimage.source
                ) {
                    navigator.navigateUp()
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = dataAuthor.title,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Text(
                    text = dataAuthor.description,
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = dataAuthor.extract,
                    modifier = Modifier.padding(horizontal = 16.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
        items(items = pagingData) { item ->
            PostUi(
                authorName = item?.author ?: "null",
                postId = item?._id ?: "null",
                content = item!!.content,
                postImg = Utils.MEDIA_URL + (1..1000).random(),
                isSpark = false,
                isBookmark = false,
                sparkOnClick = { /*TODO*/ },
                authorSlug = item.authorSlug,
                bookmarkOnClick = {},
                aboutAuthorOnClick = {}
            )
        }
    }
}