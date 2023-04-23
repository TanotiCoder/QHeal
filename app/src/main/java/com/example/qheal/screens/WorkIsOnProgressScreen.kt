package com.example.qheal.screens

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.qheal.shareComposed.DetailScreenBgImg
import com.ramcosta.composedestinations.annotation.Destination
import com.skydoves.landscapist.CircularReveal
import com.skydoves.landscapist.ShimmerParams
import com.skydoves.landscapist.coil.CoilImage


@Destination
@Composable
fun WorkIsOnProgressScreen() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Text(text = "Work is on Progress")
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Destination
@Composable
fun AboutDeveloper() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        val intent = Intent(Intent.ACTION_SENDTO)
        intent.setData(Uri.parse("https://twitter.com/Sai_Dubey_"))
        val mContext = LocalContext.current

        Card(
            modifier = Modifier.padding(32.dp),
            border = BorderStroke(2.dp, MaterialTheme.colorScheme.primary)
        ) {
            Column(
                Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {

                CoilImage(
                    imageModel = "https://raw.githubusercontent.com/TanotiCoder/SaiDubey/main/images/sai%20white%20bg.jpg",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(160.dp)
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
                Spacer(modifier = Modifier.height(8.dp))
                Text(
                    text = "Sai Prasad Dubey",
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Text(
                    text = "Recent bachelor's degree graduate skilled in Kotlin, MVVM, project creation, and PHP.",
                    style = MaterialTheme.typography.bodyLarge,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(horizontal = 16.dp),
                )
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}