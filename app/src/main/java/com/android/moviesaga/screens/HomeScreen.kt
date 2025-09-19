package com.example.moviesaga.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesaga.R
import com.example.moviesaga.designs.BottomNavigatorDesign
import com.example.moviesaga.designs.LazyRowMoviesDesign
import com.example.moviesaga.designs.LazyRowSeriesDesign
import com.example.moviesaga.tmdbMVVM.ViewModel
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Brush
import kotlinx.coroutines.delay
import com.example.moviesaga.components.VideoPlayer
import androidx.compose.runtime.getValue


@Composable
fun HomeScreen(navController: NavController, viewModel: ViewModel, goToSeriesDescScreen: (id: Int) -> Unit, goToMovieDescScreen: (id: Int) -> Unit, goToHomeScreen:()->Unit, goToMovieTabScreen:()->Unit, goToSeriesTabScreen:()->Unit, goToSearchScreen:()->Unit, goToProfileScreen:()->Unit, goToPopularCast:()->Unit, goToSettingTabScreen:()->Unit, ) {
    val popularMovies = viewModel.getPopularMovies.collectAsState().value
    val popularSeries = viewModel.getPopularSeries.collectAsState().value
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF111111),
                        Color(0xFF222222)
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "notification",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable(onClick = {
                                    goToProfileScreen()
                        })
                )

                val infiniteTransition = rememberInfiniteTransition(label = "")
                val glowAlpha by infiniteTransition.animateFloat(
                    initialValue = 0.4f,
                    targetValue = 1f,
                    animationSpec = infiniteRepeatable(
                        animation = tween(1000, easing = LinearEasing),
                        repeatMode = RepeatMode.Reverse
                    ),
                    label = "glow"
                )

                Box(
                    modifier = Modifier
                        .border(
                            width = 2.dp,
                            brush = Brush.linearGradient(
                                listOf(
                                    Color(0xFF00B4FF).copy(alpha = glowAlpha),
                                    Color(0xFF00C853).copy(alpha = glowAlpha),
                                    Color(0xFFFF9800).copy(alpha = glowAlpha)
                                )
                            ),
                            shape = RoundedCornerShape(16.dp)
                        )
                        .padding(horizontal = 12.dp, vertical = 6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.appicon),
                            contentDescription = "appicon",
                            modifier = Modifier.size(45.dp)
                        )
                        Text(
                            text = "MovieSaga",
                            fontSize = 13.sp,
                            color = Color.White,
                            fontFamily = FontFamily(Font(R.font.interbold))
                        )
                    }
                }
                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = "search",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .clip(CircleShape)
                        .padding(8.dp)
                        .clickable(
                            onClick = {
                                goToSearchScreen()
                            }
                        )
                )
            }
            @Composable
            fun AnimatedThumbnail() {
                val thumbnails = listOf(
                    R.raw.video1,
                    R.drawable.thumbnail1,
                    R.drawable.thumbnail2,
                    R.drawable.thumbnail3,
                    R.drawable.thumbnail4,
                    R.drawable.thumbnail5,
                    R.drawable.thumbnail6,
                    R.drawable.thumbnail7,
                    R.drawable.thumbnail8,
                    R.drawable.thumbnail9,
                    R.drawable.thumbnail10,
                    R.drawable.thumbnail11,
                    R.drawable.thumbnail12
                )
                var currentIndex by remember { mutableStateOf(0) }
                var isVideoPlaying by remember { mutableStateOf(true) }

                LaunchedEffect(currentIndex) {
                    if (!isVideoPlaying) {
                        delay(2500)
                        currentIndex = (currentIndex + 1) % thumbnails.size
                    }
                }
                Card(
                    modifier = Modifier
                        .padding(top = 25.dp, start = 12.dp, end = 12.dp)
                        .fillMaxWidth()
                        .height(250.dp),
                    shape = RoundedCornerShape(16.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                ) {
                    Box(modifier = Modifier.fillMaxSize()) {
                        // 🎥 Video or Image
                        if (currentIndex == 0) {
                            VideoPlayer(videoResId = R.raw.video1) {
                                isVideoPlaying = false
                                currentIndex = 1
                            }
                        } else {
                            Image(
                                painter = painterResource(id = thumbnails[currentIndex] as Int),
                                contentDescription = "Animated Thumbnail",
                                contentScale = ContentScale.Crop,
                                modifier = Modifier.fillMaxSize()
                            )
                        }

                        // 🌈 Gradient Overlay
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(
                                    Brush.verticalGradient(
                                        listOf(
                                            Color.Black.copy(alpha = 0.6f),
                                            Color.Transparent
                                        )
                                    )
                                )
                        )
                        // ✍️ Centered Text
                        Column(
                            modifier = Modifier
                                .align(Alignment.CenterStart)
                                .padding(horizontal = 20.dp)
                        ) {
                            Text(
                                text = "WELCOME",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.interbold)),
                                fontSize = 30.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "A world of movies and TV series",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.robotomedium)),
                                fontSize = 18.sp
                            )
                            Text(
                                text = "Where every story unfolds",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.robotomedium)),
                                fontSize = 16.sp
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "Explore",
                                color = Color.White,
                                fontFamily = FontFamily(Font(R.font.robotomedium)),
                                fontSize = 18.sp
                            )
                        }
                    }
                }
            }
            AnimatedThumbnail()
            if (popularMovies == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRowMoviesDesign(popularMovies, "Popular Movies", goToMovieDescScreen)
            }
            if (popularSeries == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else { // last content of page
                LazyRowSeriesDesign(popularSeries, "Popular Series", goToSeriesDescScreen)
                Spacer(modifier = Modifier.padding(top=100.dp))
            }
        }
        BottomNavigatorDesign(navController,goToHomeScreen,goToMovieTabScreen, goToSeriesTabScreen, goToPopularCast, goToSettingTabScreen )
    }
}