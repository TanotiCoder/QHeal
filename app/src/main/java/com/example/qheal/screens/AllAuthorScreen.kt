package com.example.qheal.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.items
import com.example.qheal.screens.destinations.DetailAboutAuthorScreenDestination
import com.example.qheal.screens.destinations.SearchAuthorScreenDestination
import com.example.qheal.shareComposed.TopHeaderPost
import com.example.qheal.viewmodel.AllAuthorViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AllAuthorScreen(
    allAuthorViewModel: AllAuthorViewModel = hiltViewModel(),
    navigator: DestinationsNavigator
) {
    val allAuthorData = allAuthorViewModel.allAuthorList.value.collectAsLazyPagingItems()
    LaunchedEffect(key1 = navigator) {
        allAuthorViewModel.fetchAllAuthor()
    }

    Scaffold(Modifier.fillMaxSize(),
        topBar = {
            SmallTopAppBar(modifier = Modifier.drawBehind {
                drawLine(
                    color = Color.Gray,
                    start = Offset(0f, size.height),
                    end = Offset(size.width, size.height),
                    strokeWidth = 2.dp.toPx()
                )
            },
                title = { Text(text = "Authors") },
                navigationIcon = {
                    IconButton(onClick = { navigator.navigateUp() }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = "Back Arrow"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { navigator.navigate(SearchAuthorScreenDestination()) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                })
        }) { paddingValues ->
        when (allAuthorData.loadState.refresh) {
            is LoadState.NotLoading -> {
                LazyColumn(
                    Modifier
                        .fillMaxSize()
                        .padding(paddingValues)
                ) {
                    items(allAuthorData) {
                        TopHeaderPost(name = it!!.name, slug = it.slug, clickOnMore = {}) {
                            navigator.navigate(DetailAboutAuthorScreenDestination(it.name))
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


@Composable
fun LoadingCircularBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        CircularProgressIndicator()
    }
}

@Composable
fun ErrorScreenBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Something Went Wrong")
    }
}


@Composable
fun EmptyScreenBox() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Found Nothing")
    }
}