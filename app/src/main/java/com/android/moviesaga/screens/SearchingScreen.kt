package com.example.moviesaga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R
import com.example.moviesaga.components.SearchBarComponent
import com.example.moviesaga.designs.LazyRowMoviesDesign
import com.example.moviesaga.designs.LazyRowSeriesDesign
import com.example.moviesaga.tmdbMVVM.ViewModel
import com.example.moviesaga.tmdbapidataclass.Movie.PopularTopRatedTrendingOnTheAirMoviesData
import com.example.moviesaga.tmdbapidataclass.Series.PopularTopRatedTrendingOnTheAirSeriesData
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size


@Composable
fun SearchingScreen(
    viewModel: ViewModel,
    goToMovieDescScreen: (id: Int) -> Unit,
    goToSeriesDescScreen: (id: Int) -> Unit,
    goToProfileScreen: () -> Unit
) {
    var done = remember { mutableStateOf(false) }


    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
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

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.appicon),
                        contentDescription = "appicon",
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "MovieSaga",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.interbold))
                    )
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
                )
            }
            SearchBarComponent(
                onSearch = { query ->
                    viewModel.getSearchedMovie(query)
                    viewModel.getSearchedSeries(query)
                    done.value = true
                }
            )

            if(done.value) {
                var searchedMovie: PopularTopRatedTrendingOnTheAirMoviesData? = viewModel.getSearchedMovie.collectAsState().value
                var searchedSeries: PopularTopRatedTrendingOnTheAirSeriesData? = viewModel.getSearchedSeries.collectAsState().value
                if(searchedMovie?.results?.size !=0) {
                    LazyRowMoviesDesign(movies = searchedMovie, "Movies", goToMovieDescScreen)
                }
                if(searchedSeries?.results?.size !=0) {
                    LazyRowSeriesDesign(searchedSeries, "Series", goToSeriesDescScreen)
                    Spacer(modifier = Modifier.padding(top = 100.dp))
                }
            }
        }
    }
}