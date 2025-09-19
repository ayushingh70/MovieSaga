package com.android.moviesaga.screens

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.moviesaga.tmdbMVVM.ViewModel
import androidx.navigation.NavController
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R
import com.example.moviesaga.designs.BottomNavigatorDesign
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Brush
import com.example.moviesaga.components.SearchBarComponent

@Composable
fun PopularCast(
    navController: NavController,
    viewModel: ViewModel,
    goToHomeScreen: () -> Unit,
    goToMovieTabScreen: () -> Unit,
    goToSeriesTabScreen: () -> Unit,
    goToSearchScreen: () -> Unit,
    goToProfileScreen: () -> Unit,
    goToPopularCast: () -> Unit,
    goToSettingTabScreen: () -> Unit
) {
    val peopleState by viewModel.popularPeople.collectAsState()
    val listState = rememberLazyListState()

    // ✅ Initial data load
    LaunchedEffect(Unit) {
        viewModel.getPopularPeople()
    }

    // ✅ Trigger pagination when user scrolls near bottom
    LaunchedEffect(peopleState?.results?.size ?: 0) {
        snapshotFlow { listState.layoutInfo }
            .collect { layoutInfo ->
                val lastVisible = layoutInfo.visibleItemsInfo.lastOrNull()?.index
                val total = layoutInfo.totalItemsCount

                if (lastVisible != null && total > 0 && lastVisible >= total - 4) {
                    viewModel.getPopularPeople()
                }
            }
    }
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
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF111111),
                            Color(0xFF222222)
                        )
                    )
                )
        ) {
            // 🔝 Top Bar (UNCHANGED)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "User",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable { goToProfileScreen() }
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
                    contentDescription = "Search",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .clip(CircleShape)
                        .padding(8.dp)
                        .clickable { goToSearchScreen() }
                )
            }

            Spacer(modifier = Modifier.height(12.dp))

            var searchQuery by remember { mutableStateOf("") }

            SearchBarComponent(
                modifier = Modifier
                    .padding(top = 8.dp, bottom = 8.dp),
                onSearch = { query ->
                    searchQuery = query
                }
            )

            Spacer(modifier = Modifier.height(12.dp))

            val filteredPeople = peopleState?.results?.filter {
                it.name.contains(searchQuery, ignoreCase = true)
            } ?: emptyList()


            if (peopleState == null) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator(color = Color.White)
                }
            } else if (peopleState!!.results.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No cast found", color = Color.White)
                }
            } else {
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 12.dp, end = 12.dp, bottom = 80.dp)
                ) {
                    items(filteredPeople) { person ->
                        Card(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 6.dp)
                                .clickable {
                                    navController.navigate("person_detail/${person.id}")
                                },
                            shape = RoundedCornerShape(12.dp),
                            elevation = CardDefaults.cardElevation(6.dp),
                            colors = CardDefaults.cardColors(containerColor = Color(0xFF2a2a2a))
                        ) {
                            Row(
                                modifier = Modifier
                                    .padding(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                AsyncImage(
                                    model = "https://image.tmdb.org/t/p/w185${person.profile_path}",
                                    contentDescription = person.name,
                                    modifier = Modifier
                                        .size(64.dp)
                                        .clip(CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                                Spacer(modifier = Modifier.width(12.dp))
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = person.name,
                                        style = MaterialTheme.typography.titleMedium.copy(fontSize = 17.sp),
                                        color = Color.White
                                    )
                                    Text(
                                        text = person.known_for_department,
                                        style = MaterialTheme.typography.bodySmall.copy(fontSize = 13.sp),
                                        color = Color.Gray
                                    )
                                }
                            }
                        }
                    }

                    // Optional: show loader while fetching next page
                    if (peopleState?.results?.isNotEmpty() == true) {
                        item {
                            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                                CircularProgressIndicator(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .size(24.dp),
                                    color = Color.Gray
                                )
                            }
                        }
                    }
                }
            }
        }
    }

    // Bottom Navigation (UNCHANGED)
    BottomNavigatorDesign(
        navController,
        goToHomeScreen,
        goToMovieTabScreen,
        goToSeriesTabScreen,
        goToPopularCast,
        goToSettingTabScreen
    )
}
