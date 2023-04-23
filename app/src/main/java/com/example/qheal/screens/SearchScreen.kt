package com.example.qheal.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.qheal.R
import com.example.qheal.screens.destinations.DetailAboutAuthorScreenDestination
import com.example.qheal.shareComposed.TopHeaderPost
import com.example.qheal.viewmodel.AllAuthorViewModel
import com.example.qheal.viewmodel.QuerySearchViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.delay

@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchQuotesScreen(
    querySearchViewModel: QuerySearchViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    var queryText by remember { mutableStateOf("") }
    val queryData = querySearchViewModel.queryData.value.collectAsLazyPagingItems()
    LaunchedEffect(key1 = queryText) {
        if (querySearchViewModel.querySearch.value.trim().length != querySearchViewModel.preQuerySearch.value.trim().length
            && querySearchViewModel.querySearch.value.trim().isNotEmpty()
        ) {
            delay(500)
            querySearchViewModel.hitQuerySearch()
            querySearchViewModel.preQuerySearch.value = queryText
        }
    }

    Scaffold(topBar = {
        SmallTopAppBar(navigationIcon = {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        }, title = {
            Box(modifier = Modifier.padding(4.dp)) {
                OutlinedTextField(value = queryText, onValueChange = {
                    queryText = it
                    querySearchViewModel.querySearch.value = queryText
                },
                    label = {
                        Text(text = stringResource(id = R.string.search_author_and_quotes))
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        })
    }) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (queryData.loadState.refresh) {
                is LoadState.NotLoading -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                        items(items = queryData) {
                            if (it == null) {
                                EmptyScreenBox()
                            } else {
                                SearchCardPost(content = it.content, authorName = it.author)
                            }
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
}


@Destination
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchAuthorScreen(allAuthorViewModel: AllAuthorViewModel = hiltViewModel(),navigator: DestinationsNavigator) {
    var queryText by remember { mutableStateOf("") }
    val queryData = allAuthorViewModel.authorQueryState.value.collectAsLazyPagingItems()
    LaunchedEffect(key1 = queryText) {
        if (allAuthorViewModel.authorQuerySearch.value.trim().length != allAuthorViewModel.preAuthorQuerySearch.value.trim().length
            && allAuthorViewModel.authorQuerySearch.value.trim().isNotEmpty()
        ) {
            delay(500)
            allAuthorViewModel.hitAuthorSearch()
            allAuthorViewModel.preAuthorQuerySearch.value = queryText
        }
    }

    Scaffold(topBar = {
        SmallTopAppBar(navigationIcon = {
            IconButton(onClick = { navigator.navigateUp() }) {
                Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Arrow Back")
            }
        }, title = {
            Box(modifier = Modifier.padding(4.dp)) {
                OutlinedTextField(value = queryText, onValueChange = {
                    queryText = it
                    allAuthorViewModel.authorQuerySearch.value = queryText
                },
                    label = {
                        Text(text = stringResource(id = R.string.search_author))
                    },
                    singleLine = true,
                    textStyle = MaterialTheme.typography.bodyLarge
                )
            }
        })
    }) { it ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (queryData.loadState.refresh) {
                is LoadState.NotLoading -> {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(items = queryData) {
                            if (it == null) {
                                EmptyScreenBox()
                            } else {
                                TopHeaderPost(name = it.name, slug = it.slug, clickOnMore = {}) {
                                    navigator.navigate(DetailAboutAuthorScreenDestination(it.name))
                                }
                            }
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


}




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchCardPost(content: String?, authorName: String?) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp), contentAlignment = Alignment.Center
    ) {
        Card(
            Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp)),
            border = BorderStroke(1.dp, color = Color.Gray),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Column(
                Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = content!!,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Start
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = authorName ?: "Null",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.End,
                    style = MaterialTheme.typography.bodyMedium,
                    fontStyle = FontStyle.Italic
                )
            }
        }
    }
}