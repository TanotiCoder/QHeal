package com.example.qheal.shareComposed

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.qheal.utils.Utils.AUTHOR_IMAGE_URL


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorChipsArrow(chipOnClick: () -> Unit) {
    TextButton(onClick = { chipOnClick() }) {
        Text(
            text = "show More",
            overflow = TextOverflow.Ellipsis,
            maxLines = 1,
            textAlign = TextAlign.Center,
            color = Color.Gray
            //style = MaterialTheme.typography.labelSmall
        )
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
            imageVector = Icons.Default.ArrowForward,
            contentDescription = "Forward",
            tint = Color.Gray
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorNameChips(authorSlug: String, authorName: String, chipOnClick: (String) -> Unit) {
    Card(
        Modifier
            .clickable { chipOnClick(authorSlug) }
            .height(40.dp)
            .widthIn(60.dp, 120.dp),
        shape = RoundedCornerShape(20.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
    ) {
        Row(
            Modifier.padding(4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AuthorImage(authorSlug = authorSlug, modifier = Modifier)
            AuthorName(AuthorName = authorName)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthorImage(modifier: Modifier, authorSlug: String, size: Dp = 32.dp) {
    Card(
        modifier = modifier
            .size(size)
            .clip(RoundedCornerShape(size / 2))
            .fillMaxSize(),
    ) {
        CoilCircleImg(
            url = "$AUTHOR_IMAGE_URL$authorSlug.jpg",
            size = size,
        )
    }
}


@Composable
fun AuthorName(AuthorName: String) {
    Text(
        text = AuthorName.replaceFirstChar { it.uppercase() },
        overflow = TextOverflow.Ellipsis,
        maxLines = 1,
        textAlign = TextAlign.Center,
        color = Color.White,
        style = MaterialTheme.typography.labelSmall
    )
}
