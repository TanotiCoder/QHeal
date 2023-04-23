package com.example.qheal.screens

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.rounded.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.Navigation
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.qheal.R
import com.example.qheal.screens.destinations.AboutDeveloperDestination
import com.example.qheal.screens.destinations.AllAuthorScreenDestination
import com.example.qheal.screens.destinations.SearchQuotesScreenDestination
import com.example.qheal.screens.destinations.WorkIsOnProgressScreenDestination
import com.example.qheal.shareComposed.PostCardColum
import com.example.qheal.viewmodel.HomeViewModel
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Destination(start = true)
@Composable
fun HomeScreen(
    homeViewModel: HomeViewModel = hiltViewModel(),
    navigator: DestinationsNavigator,
) {
    val quotesPagingData = homeViewModel.quoteFlow.value.collectAsLazyPagingItems()
    val authorNameData = homeViewModel.authorChipName
    val tagButton = homeViewModel.tagButton
    val drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope: CoroutineScope = rememberCoroutineScope()
    var selectedScreen by remember {
        mutableStateOf(0)
    }
    val navigationDrawerItemList = listOf(
        NavigationDrawerItemModel(Icons.Rounded.Home, "Home"),
        NavigationDrawerItemModel(Icons.Rounded.Edit, "All Author"),
        NavigationDrawerItemModel(Icons.Rounded.Bolt, "Spark"),
        NavigationDrawerItemModel(Icons.Rounded.BookmarkBorder, "Bookmark"),
        NavigationDrawerItemModel(Icons.Rounded.Code, "About Developer"),
    )

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            Spacer(modifier = Modifier.height(12.dp))
            navigationDrawerItemList.forEachIndexed { index, navigationDrawerItemModel ->
                NavigationDrawerItem(
                    icon = {
                        Icon(
                            imageVector = navigationDrawerItemModel.icon,
                            contentDescription = "null"
                        )
                    },
                    label = { Text(text = navigationDrawerItemModel.label) },
                    onClick = {
                        when (index) {
                            0 -> {
                                scope.launch { drawerState.close() }
                            }
                            1 -> {
                                navigator.navigate(AllAuthorScreenDestination())
                                selectedScreen = index
                                scope.launch { drawerState.close() }
                            }
                            4->{
                                navigator.navigate(AboutDeveloperDestination())
                                selectedScreen = index
                                scope.launch { drawerState.close() }
                            }
                            else -> {
                                navigator.navigate(WorkIsOnProgressScreenDestination())
                                selectedScreen = index
                                scope.launch { drawerState.close() }
                            }
                        }
                    },
                    selected = index == selectedScreen,
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                    colors = NavigationDrawerItemDefaults.colors(
                        selectedContainerColor = MaterialTheme.colorScheme.primary.copy(alpha = 0.1f),
                    ),
                )
            }
        }
    ) {
        Scaffold(topBar = {
            SmallTopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.app_name),
                        color = MaterialTheme.colorScheme.primary
                    )
                },
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = Color.Gray,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = 2.dp.toPx()
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        scope.launch { drawerState.open() }
                        //navigateOnClick(route[index])
                    }) {
                        Icon(imageVector = Icons.Default.Menu, contentDescription = "Menu")
                    }
                },
                actions = {
                    IconButton(onClick = { navigator.navigate(SearchQuotesScreenDestination()) }) {
                        Icon(imageVector = Icons.Default.Search, contentDescription = "Search")
                    }
                },
            )
        }) { paddingValues ->
            PostCardColum(
                pagingData = quotesPagingData,
                authorNameData = authorNameData,
                tagButton = tagButton,
                paddingValues = paddingValues,
                navigator = navigator
            )
        }
    }
}


data class NavigationDrawerItemModel(val icon: ImageVector, val label: String)