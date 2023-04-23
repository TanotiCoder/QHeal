package com.example.qheal.shareComposed

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Close
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qheal.data.remote.responce.TagButtonResponse
import kotlin.math.max


@Composable
fun TagsFlowRow(
    tagData: List<TagButtonResponse>,
    modifier: Modifier,
    tagQuotesOnClick: (String) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        StaggeredGrid(
            modifier = modifier
                .horizontalScroll(rememberScrollState())
        ) {
            StaggeredGrid {
                tagData.forEach {
                    Box(
                        modifier
                            .padding(top = 4.dp, bottom = 4.dp, end = 8.dp)
                    )
                    {
                        TagTextAndCount(
                            tagsName = it.name,
                            tagsCount = it.quoteCount,
                            modifier,
                            tagQuotesOnClick = { tagQuotesOnClick(it) }
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TagTextAndCount(
    tagsName: String,
    tagsCount: Int,
    modifier: Modifier,
    tagQuotesOnClick: (String) -> Unit
) {
    if (tagsCount != 0) {
        Box(
            modifier = modifier
                .clickable { tagQuotesOnClick(tagsName) }
                .border(
                    width = 1.dp,
                    color = MaterialTheme.colorScheme.primary,
                    shape = RoundedCornerShape(100.dp)
                )
                .padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text = tagsName,
                    textAlign = TextAlign.Center,
                    style = MaterialTheme.typography.bodyMedium,
                )
                CountTag(count = tagsCount, modifier)
            }
        }
    }
}

@Composable
fun CountTag(
    count: Int,
    modifier: Modifier
) {
    Box {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Center
        ) {
            Divider(
                color = Color.Gray,
                modifier = modifier
                    .height(16.dp)
                    .width(1.dp)
            )
            Text(
                text = count.toString(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
                modifier = modifier.padding(horizontal = 4.dp)
            )
        }
    }
}


@Composable
private fun StaggeredGrid(
    modifier: Modifier = Modifier,
    rows: Int = 3,
    content: @Composable () -> Unit
) {
    Layout(
        content = content,
        modifier = modifier.padding(8.dp)
    ) { measurable, constraints ->
        val rowWidths = IntArray(rows) { 0 } // Keep track of the width of each row
        val rowHeights = IntArray(rows) { 0 } // Keep track of the height of each row

        // Don't constrain child views further, measure them with given constraints
        val placeable = measurable.mapIndexed { index, measurable ->
            val placeable = measurable.measure(constraints)

            // Track the width and max height of each row
            val row = index % rows
            rowWidths[row] += placeable.width
            rowHeights[row] = max(rowHeights[row], placeable.height)

            placeable
        }

        // Grid's width is the widest row
        val width = rowWidths.maxOrNull()?.coerceIn(constraints.minWidth, constraints.maxWidth)
            ?: constraints.minWidth
        // Grid's height is the sum of each row
        val height = rowHeights.sum().coerceIn(constraints.minHeight, constraints.maxHeight)

        // y co-ord of each row
        val rowY = IntArray(rows) { 0 }
        for (i in 1 until rows) {
            rowY[i] = rowY[i - 1] + rowHeights[i - 1]
        }
        layout(width, height) {
            // x co-ord we have placed up to, per row
            val rowX = IntArray(rows) { 0 }
            placeable.forEachIndexed { index, placeable ->
                val row = index % rows
                placeable.place(
                    x = rowX[row],
                    y = rowY[row]
                )
                rowX[row] += placeable.width
            }
        }
    }
}


@Composable
fun TagBackButton(tagsName: String, modifier: Modifier, goBack: () -> Unit) {
    Box(
        modifier = modifier
            //.clickable { tagQuotesOnClick(tagsName) }
            .border(
                width = 1.dp,
                color = MaterialTheme.colorScheme.primary,
                shape = RoundedCornerShape(100.dp)
            )
            .padding(horizontal = 12.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            IconButton(onClick = { goBack() }) {
                Icon(imageVector = Icons.Rounded.Close, contentDescription = "Close")
            }
            Divider(
                color = Color.Gray,
                modifier = modifier
                    .height(24.dp)
                    .width(1.dp)
            )
            Text(
                text = tagsName,
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.bodyMedium,
            )

        }
    }
}