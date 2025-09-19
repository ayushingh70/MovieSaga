package com.example.moviesaga.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
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
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesaga.R
import com.example.moviesaga.designs.BottomNavigatorDesign
import com.example.moviesaga.designs.LazyRowMoviesDesign
import com.example.moviesaga.tmdbMVVM.ViewModel

@Composable
fun MovieTabScreen(
    navController: NavController,
    viewModel: ViewModel,
    goToHomeScreen: () -> Unit,
    goToMovieTabScreen: () -> Unit,
    goToMovieDescScreen: (id: Int) -> Unit,
    goToSeriesTabScreen:()->Unit,
    goToSearchScreen:()->Unit,
    goToProfileScreen:()->Unit,
    goToPopularCast:()->Unit,
    goToSettingTabScreen:()->Unit
) {
    var timeWindow by remember{
        mutableStateOf("week")
    }
    if(viewModel.getPopularMovies.collectAsState().value==null) {
        viewModel.getPopularMovies()
    }

    if(viewModel.getTopRatedMovies.collectAsState().value==null) {
        viewModel.getTopRatedMovies()
    }
    if(viewModel.getNowPlayingMovies.collectAsState().value==null) {
        viewModel.getNowPlayingMovies()
    }
    if(viewModel.getUpcomingMovies.collectAsState().value==null) {
        viewModel.getUpcomingMovies()
    }
    if(viewModel.getTrendingMovies.collectAsState().value==null){
        viewModel.getTrendingMovies(timeWindow)
    }
    val popularMovies = viewModel.getPopularMovies.collectAsState().value
    val topRatedMovies = viewModel.getTopRatedMovies.collectAsState().value
    val nowPlayingMovies = viewModel.getNowPlayingMovies.collectAsState().value
    val upcomingMovies = viewModel.getUpcomingMovies.collectAsState().value
    val trendingMovies = viewModel.getTrendingMovies.collectAsState().value

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
                // movies list

            if (nowPlayingMovies == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRowMoviesDesign(nowPlayingMovies, "Now Playing", goToMovieDescScreen)
            }
            if (topRatedMovies == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRowMoviesDesign(topRatedMovies, "Top Rated Movies", goToMovieDescScreen)
            }

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

            if (upcomingMovies == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRowMoviesDesign(upcomingMovies, "Upcoming Movies", goToMovieDescScreen)
            }

            if (trendingMovies == null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(280.dp), contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            } else {
                LazyRowMoviesDesign(trendingMovies, "Trending Movies", goToMovieDescScreen)
                Spacer(modifier = Modifier.padding(bottom = 100.dp))
            }
        }
    }
    BottomNavigatorDesign(navController,goToHomeScreen, goToMovieTabScreen, goToSeriesTabScreen, goToPopularCast, goToSettingTabScreen)
}