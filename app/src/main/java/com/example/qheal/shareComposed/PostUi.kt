package com.example.qheal.shareComposed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.BookmarkBorder
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.qheal.R
import com.example.qheal.data.local.PostEntity
import com.example.qheal.utils.Utils.AUTHOR_IMAGE_URL
import com.example.qheal.viewmodel.LocalPostViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PostUi(
    authorName: String,
    postId: String,
    authorSlug: String,
    content: String,
    postImg: String,
    isSpark: Boolean,
    isBookmark: Boolean,
    aboutAuthorOnClick: () -> Unit,
    sparkOnClick: () -> Unit,
    bookmarkOnClick: () -> Unit,
    localPostViewModel: LocalPostViewModel = hiltViewModel()
) {

    LaunchedEffect(key1 = postId) {
        localPostViewModel.exist(postId)
    }

    //val spark by remember { mutableStateOf(localPostViewModel.exist.value == 1) }
    val spark = localPostViewModel.exist.value == 1
    var bookmark by remember { mutableStateOf(false) }


    Card(Modifier.widthIn(192.dp, 720.dp), elevation = CardDefaults.cardElevation(4.dp)) {
        Column(Modifier.fillMaxSize()) {
            TopHeaderPost(
                name = authorName,
                slug = authorSlug,
                clickOnMore = {},
                clickOnAboutAuthor = aboutAuthorOnClick
            )
            ImageForPost(url = postImg)
            Box(Modifier.padding(16.dp)) {
                //Text(text = content, style = MaterialTheme.typography.titleSmall, maxLines = 4)
                ExpandingText(text = content)
            }
            PostActionButton(
                isSpark = spark,
                isBookmark = bookmark,
                sparkOnClick = {
                    if (spark) {
                        localPostViewModel.removePostFromRoom(postId)
                    } else {
                        localPostViewModel.insertPostInRoom(
                            PostEntity(
                                _id = postId,
                                author = authorName,
                                authorSlug = authorSlug,
                                content,
                                addedOn = SimpleDateFormat.getDateInstance().format(Date())
                            )
                        )
                    }
                   // spark = !spark
                },
                bookmarkOnClick = { bookmark = !bookmark }
            )
        }
    }
}


@Composable
fun PostActionButton(
    isSpark: Boolean,
    isBookmark: Boolean,
    sparkOnClick: () -> Unit,
    bookmarkOnClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .height(64.dp)
            .fillMaxWidth()
            .padding(vertical = 4.dp)
        // .background(abc)
        , contentAlignment = Alignment.Center
    ) {
        ButtonRow(
            modifier = Modifier,
            isSpark = isSpark,
            isBookmark = isBookmark,
            sparkOnClick = sparkOnClick,
            bookmarkOnClick = bookmarkOnClick
        )
    }
}


@Composable
fun ButtonRow(
    modifier: Modifier,
    isSpark: Boolean,
    isBookmark: Boolean,
    sparkOnClick: () -> Unit,
    bookmarkOnClick: () -> Unit
) {
    Column(
        modifier = modifier.padding(bottom = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Divider(
            color = Color.Gray,
            modifier = modifier
                .height(1.dp)
                .fillMaxWidth()
                .padding(horizontal = 12.dp)
        )
        Row(
            modifier = modifier
                .height(40.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            IconButton(
                modifier = modifier.weight(1f),
                onClick = { sparkOnClick() }
            ) {
                if (isSpark)
                    Icon(
                        painter = painterResource(id = R.drawable.spark_filled),
                        contentDescription = "Spark_filled",
                        tint = MaterialTheme.colorScheme.primary
                    ) else {
                    Icon(
                        painter = painterResource(id = R.drawable.spark_outlined),
                        contentDescription = "Spark_outlined",
                    )
                }
            }
            Divider(
                color = Color.Gray,
                modifier = modifier
                    .height(44.dp)
                    .width(1.dp)
            )
            IconButton(
                modifier = modifier.weight(1f),
                onClick = { bookmarkOnClick() }
            ) {
                if (isBookmark)
                    Icon(
                        imageVector = Icons.Default.Bookmark,
                        contentDescription = "Bookmark_filled"
                    ) else {
                    Icon(
                        imageVector = Icons.Default.BookmarkBorder,
                        contentDescription = "Bookmark_border"
                    )
                }
            }

        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ImageForPost(url: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(192.dp)
            .widthIn(min = 192.dp, max = 720.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        CoilRectangleImg(url = url)
    }
}


@Composable
fun TopHeaderPost(
    name: String,
    slug: String,
    clickOnMore: () -> Unit,
    clickOnAboutAuthor: () -> Unit
) {
    Row(
        Modifier
            .fillMaxWidth()
            .height(64.dp)
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            modifier = Modifier
                .weight(1f)
        ) {
            Box(Modifier.size(40.dp), contentAlignment = Alignment.Center) {
                CoilCircleImg(
                    url = "$AUTHOR_IMAGE_URL$slug.jpg",
                    size = 40.dp,
                )
            }
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier
                    .clickable { clickOnAboutAuthor() }
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = name,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleSmall
                )
                Text(
                    text = "@$slug",
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    color = Color.Gray,
                    style = MaterialTheme.typography.titleSmall
                )
            }
        }
        IconButton(onClick = { clickOnMore() }) {
            Icon(imageVector = Icons.Default.MoreVert, contentDescription = "MoreVert")
        }
    }
}
