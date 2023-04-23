package com.example.qheal.shareComposed

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoilCircleImg(size: Dp = 40.dp, url: String, circularRevealDuration: Int = 0) {
    Card(modifier = Modifier.size(size = size), shape = CircleShape) {
        CoilImage(
            imageModel = url,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(size)
                .fillMaxSize()
                .clip(CircleShape),
            shimmerParams = ShimmerParams(
                baseColor = Color.Gray,
                highlightColor = Color.LightGray,
                intensity = 20f,
                durationMillis = 500
            ),
            failure = {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                }
            },
            circularReveal = CircularReveal(duration = circularRevealDuration)
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoilRectangleImg(url: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(192.dp)
            .widthIn(192.dp, 720.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        CoilImage(
            imageModel = url,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            shimmerParams = ShimmerParams(
                baseColor = Color.Gray,
                highlightColor = Color.LightGray,
                intensity = 20f,
                durationMillis = 500
            ),

            failure = {
                Box(Modifier.fillMaxSize()) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CoilRectangleBlurImg(url: String) {
    Card(
        Modifier
            .fillMaxWidth()
            .height(192.dp)
            .widthIn(192.dp, 720.dp),
        shape = RoundedCornerShape(0.dp)
    ) {
        CoilImage(
            imageModel = url,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize(),
            shimmerParams = ShimmerParams(
                baseColor = Color.Gray,
                highlightColor = Color.LightGray,
                intensity = 20f,
                durationMillis = 500
            ),
            alpha = .5f,
            failure = {
                Box(Modifier.fillMaxSize()) {
                    Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                }
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailScreenBgImg(url: String, backOnClick: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight(.4f),
        contentAlignment = Alignment.Center
    ) {
        ConstraintLayout(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            val (backButton, circularImg, rectangleImg, blackBox) = createRefs()

            CoilImage(
                imageModel = url,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(240.dp)
                    .constrainAs(rectangleImg) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                    },
                shimmerParams = ShimmerParams(
                    baseColor = Color.Gray,
                    highlightColor = Color.LightGray,
                    intensity = 20f,
                    durationMillis = 500
                ),
                alpha = .7f,
                failure = {
                    Box() {
                        Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                    }
                }
            )
            Row(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(backButton) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    }
                    .height(64.dp)
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Card {
                    IconButton(onClick = { backOnClick() }) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                }
            }
            Box(
                Modifier
                    .fillMaxWidth()
                    .constrainAs(circularImg) {
                        end.linkTo(parent.end)
                        start.linkTo(parent.start)
                        bottom.linkTo(parent.bottom)
                    }, contentAlignment = Alignment.BottomCenter
            ) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(120.dp)
                        .background(
                            brush = Brush.verticalGradient(
                                listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.background.copy(alpha = 0.8F),
                                    MaterialTheme.colorScheme.background
                                ),
                                startY = 0.5F
                            )
                        )
                )
                CoilImage(
                    imageModel = url,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape),
                    shimmerParams = ShimmerParams(
                        baseColor = Color.Gray,
                        highlightColor = Color.LightGray,
                        intensity = 20f,
                        durationMillis = 500
                    ),
                    failure = {
                        Box(contentAlignment = Alignment.Center) {
                            Icon(imageVector = Icons.Default.Person, contentDescription = "Person")
                        }
                    },
                    circularReveal = CircularReveal(duration = 500)
                )
            }
        }
    }
}