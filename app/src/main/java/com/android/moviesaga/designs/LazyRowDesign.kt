package com.example.moviesaga.designs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil3.compose.rememberAsyncImagePainter
import com.example.moviesaga.R
import com.example.moviesaga.tmdbapidataclass.Movie.MovieDetailsData
import com.example.moviesaga.tmdbapidataclass.Movie.PopularTopRatedTrendingMoviesResult
import com.example.moviesaga.tmdbapidataclass.Movie.PopularTopRatedTrendingOnTheAirMoviesData
import com.example.moviesaga.tmdbapidataclass.Series.PopularSeriesResult
import com.example.moviesaga.tmdbapidataclass.Series.PopularTopRatedTrendingOnTheAirSeriesData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesDetailsOneData
import kotlin.math.roundToInt
import androidx.compose.animation.core.*
import androidx.compose.runtime.*

@Composable
fun LazyRowMoviesDesign(movies: PopularTopRatedTrendingOnTheAirMoviesData?, heading: String, goToMovieDescScreen: (id: Int) -> Unit) {
    if (movies != null) {
        val result: List<PopularTopRatedTrendingMoviesResult> = movies.results

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val animatedOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 15000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ), label = ""
        )
         // Glowing animated border
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .height(350.dp)
                    .padding(start = 12.dp, end = 12.dp, top = 25.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00B4FF).copy(alpha = 0.7f),
                                Color(0xFF00C853).copy(alpha = 0.7f),
                                Color(0xFFFF9800).copy(alpha = 0.7f),
                                Color(0xFF00B4FF).copy(alpha = 0.7f),
                            ),
                            start = Offset(animatedOffset, 0f),
                            end = Offset(0f, animatedOffset)
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF2e2d2d)),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = heading,
                        modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 20.sp
                    )
                    LazyRow(
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .fillMaxWidth()
                            .background(Color(0xFF2e2d2d))
                    ){
                        items(result) {
                            Card(
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp)
                                    .width(120.dp)
                                    .height(280.dp)
                                    .clickable { goToMovieDescScreen(it.id) }
                            ) {
                                if (it.poster_path != null && it.poster_path.isNotEmpty()) {
                                    Card(modifier = Modifier.background(Color(0xFF2e2d2d))) {
                                        Image(
                                            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w300/${it.poster_path}"),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxWidth(),
                                            contentScale = ContentScale.FillWidth
                                        )
                                    }
                                }
                                Column(
                                    verticalArrangement = Arrangement.SpaceBetween,
                                    modifier = Modifier
                                        .background(Color(0xFF2e2d2d))
                                        .padding(8.dp)
                                        .fillMaxWidth()
                                        .fillMaxHeight()
                                ) {
                                    if (it.title.isNotEmpty()) {
                                        Text(
                                            text = it.title,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 2.dp, bottom = 10.dp)
                                                .align(Alignment.Start),
                                            color = Color(0xFFE0E0E0),
                                            fontFamily = FontFamily(Font(R.font.interfont)),
                                            fontSize = 16.sp,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                    }
                                    Row(
                                        modifier = Modifier
                                            .padding(2.dp)
                                            .fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            Image(
                                                painterResource(id = R.drawable.star),
                                                contentDescription = null,
                                                modifier = Modifier.size(13.dp)
                                            )
                                            Text(
                                                text = ((it.vote_average * 10).roundToInt() / 10.0).toString(),
                                                color = Color(0xFF7091C2),
                                                fontFamily = FontFamily(Font(R.font.robotolight)),
                                                modifier = Modifier.padding(start = 2.dp)
                                            )
                                        }
                                        if (it.release_date.isNotEmpty()) {
                                            Text(
                                                text = it.release_date.substring(0, 4),
                                                color = Color(0xFF7091C2),
                                                fontFamily = FontFamily(Font(R.font.robotolight)),
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

@Composable
fun LazyRowMoviesFavouriteWatchListDesign(
    movies: List<MovieDetailsData?>,
    heading: String,
    goToMovieDescScreen: (id: Int) -> Unit
) {
    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 2000f, // Bigger value for seamless looping
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 15000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = ""
    )

    if (movies.isNotEmpty()) {
            // Gradient border wrapper
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .height(350.dp)
                .padding(start = 12.dp, end = 12.dp, top = 25.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00B4FF).copy(alpha = 0.7f),
                            Color(0xFF00C853).copy(alpha = 0.7f),
                            Color(0xFFFF9800).copy(alpha = 0.7f),
                            Color(0xFF00B4FF).copy(alpha = 0.7f),
                        ),
                        start = Offset(animatedOffset, 0f),
                        end = Offset(0f, animatedOffset)
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2e2d2d)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = heading,
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                    color = Color(0xFFE0E0E0),
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    fontSize = 20.sp
                )
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2e2d2d))
                ){
                        items(movies) { it ->
                            if (it != null) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp)
                                        .width(120.dp)
                                        .height(280.dp)
                                        .clickable { goToMovieDescScreen(it.id) }
                                ) {
                                    Column {
                                        if (it.poster_path.isNotEmpty()) {
                                            Image(
                                                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w300/${it.poster_path}"),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxWidth(),
                                                contentScale = ContentScale.FillWidth
                                            )
                                        }

                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .background(Color(0xFF2e2d2d))
                                                .padding(8.dp)
                                                .fillMaxSize()
                                        ) {
                                            if (it.title.isNotEmpty()) {
                                                Text(
                                                    text = it.title,
                                                    modifier = Modifier
                                                        .fillMaxWidth()
                                                        .padding(start = 2.dp, bottom = 10.dp)
                                                        .align(Alignment.Start),
                                                    color = Color(0xFFE0E0E0),
                                                    fontFamily = FontFamily(Font(R.font.interfont)),
                                                    fontSize = 16.sp,
                                                    maxLines = 2,
                                                    overflow = TextOverflow.Ellipsis
                                                )
                                            }
                                            Row(
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.star),
                                                        contentDescription = null,
                                                        tint = Color.Unspecified,
                                                        modifier = Modifier.size(13.dp)
                                                    )
                                                    Text(
                                                        text = ((it.vote_average * 10).roundToInt() / 10.0).toString(),
                                                        color = Color(0xFF7091C2),
                                                        fontFamily = FontFamily(Font(R.font.robotolight)),
                                                        modifier = Modifier.padding(start = 2.dp)
                                                    )
                                                }
                                                if (it.release_date.isNotEmpty()) {
                                                    Text(
                                                        text = it.release_date.substring(0, 4),
                                                        color = Color(0xFF7091C2),
                                                        fontFamily = FontFamily(Font(R.font.robotolight)),
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = 7.dp, end = 7.dp, top = 30.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2e2d2d)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Movies Found",
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                    color = Color(0xFFE0E0E0),
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Composable
fun LazyRowSeriesDesign(
    movies: PopularTopRatedTrendingOnTheAirSeriesData?,
    heading: String,
    goToSeriesDescScreen: (id: Int) -> Unit
) {
    if (movies != null) {
        val result: List<PopularSeriesResult> = movies.results

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val animatedOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 15000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = ""
        )

        // Glowing animated border
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(20.dp))
                .height(350.dp)
                .padding(start = 12.dp, end = 12.dp, top = 25.dp)
                .background(
                    brush = Brush.linearGradient(
                        colors = listOf(
                            Color(0xFF00B4FF).copy(alpha = 0.7f),
                            Color(0xFF00C853).copy(alpha = 0.7f),
                            Color(0xFFFF9800).copy(alpha = 0.7f),
                            Color(0xFF00B4FF).copy(alpha = 0.7f),
                        ),
                        start = Offset(animatedOffset, 0f),
                        end = Offset(0f, animatedOffset)
                    ),
                    shape = RoundedCornerShape(22.dp)
                )
                .padding(2.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2e2d2d)),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = heading,
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                    color = Color(0xFFE0E0E0),
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    fontSize = 20.sp
                )
                LazyRow(
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .fillMaxWidth()
                        .background(Color(0xFF2e2d2d))
                ){
                    items(result) {
                            Card(
                                modifier = Modifier
                                    .padding(start = 4.dp, end = 4.dp)
                                    .width(120.dp)
                                    .height(280.dp)
                                    .clickable { goToSeriesDescScreen(it.id) }
                            ) {
                                Column {
                                    if (it.poster_path != null && it.poster_path.isNotEmpty()) {
                                        Image(
                                            painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w300/${it.poster_path}"),
                                            contentDescription = null,
                                            modifier = Modifier.fillMaxWidth(),
                                            contentScale = ContentScale.FillWidth
                                        )
                                    }

                                    Column(
                                        verticalArrangement = Arrangement.SpaceBetween,
                                        modifier = Modifier
                                            .background(Color(0xFF2e2d2d))
                                            .padding(8.dp)
                                            .fillMaxSize()
                                    ) {
                                        Text(
                                            text = it.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(start = 2.dp, bottom = 10.dp)
                                                .align(Alignment.Start),
                                            color = Color(0xFFE0E0E0),
                                            fontFamily = FontFamily(Font(R.font.interfont)),
                                            fontSize = 16.sp,
                                            maxLines = 2,
                                            overflow = TextOverflow.Ellipsis
                                        )
                                        Row(
                                            modifier = Modifier
                                                .padding(2.dp)
                                                .fillMaxWidth(),
                                            horizontalArrangement = Arrangement.SpaceBetween
                                        ) {
                                            Row(verticalAlignment = Alignment.CenterVertically) {
                                                Icon(
                                                    painter = painterResource(id = R.drawable.star),
                                                    contentDescription = null,
                                                    tint = Color.Unspecified,
                                                    modifier = Modifier.size(13.dp)
                                                )
                                                Text(
                                                    text = ((it.vote_average * 10).roundToInt() / 10.0).toString(),
                                                    color = Color(0xFF7091C2),
                                                    fontFamily = FontFamily(Font(R.font.robotolight)),
                                                    modifier = Modifier.padding(start = 2.dp)
                                                )
                                            }
                                            if (it.first_air_date.isNotEmpty()) {
                                                Text(
                                                    text = it.first_air_date.substring(0, 4),
                                                    color = Color(0xFF7091C2),
                                                    fontFamily = FontFamily(Font(R.font.robotolight)),
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


@Composable
fun LazyRowFavouriteWatchlistSeriesDesign(
    movies: List<SeriesDetailsOneData?>,
    heading: String,
    goToSeriesDescScreen: (id: Int) -> Unit
) {
    if (movies.isNotEmpty()) {

        val infiniteTransition = rememberInfiniteTransition(label = "")
        val animatedOffset by infiniteTransition.animateFloat(
            initialValue = 0f,
            targetValue = 2000f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 15000, easing = LinearEasing),
                repeatMode = RepeatMode.Restart
            ),
            label = ""
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp)
                .background(Color(0xFF1f1f1f))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(350.dp)
                    .background(
                        brush = Brush.linearGradient(
                            colors = listOf(
                                Color(0xFF00B4FF).copy(alpha = 0.7f),
                                Color(0xFF00C853).copy(alpha = 0.7f),
                                Color(0xFFFF9800).copy(alpha = 0.7f),
                                Color(0xFF00B4FF).copy(alpha = 0.7f),
                            ),
                            start = Offset(animatedOffset, 0f),
                            end = Offset(0f, animatedOffset)
                        ),
                        shape = RoundedCornerShape(22.dp)
                    )
                    .padding(2.dp)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .clip(RoundedCornerShape(20.dp))
                        .background(Color(0xFF2e2d2d)),
                    verticalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = heading,
                        modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 20.sp
                    )
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 8.dp)
                    ) {
                        items(movies) {
                            if (it != null) {
                                Card(
                                    modifier = Modifier
                                        .padding(start = 4.dp, end = 4.dp)
                                        .width(120.dp)
                                        .height(280.dp)
                                        .clickable { goToSeriesDescScreen(it.id) }
                                ) {
                                    Column {
                                        if (!it.poster_path.isNullOrEmpty()) {
                                            Image(
                                                painter = rememberAsyncImagePainter("https://image.tmdb.org/t/p/w300/${it.poster_path}"),
                                                contentDescription = null,
                                                modifier = Modifier.fillMaxWidth(),
                                                contentScale = ContentScale.FillWidth
                                            )
                                        }
                                        Column(
                                            verticalArrangement = Arrangement.SpaceBetween,
                                            modifier = Modifier
                                                .background(Color(0xFF2e2d2d))
                                                .padding(8.dp)
                                                .fillMaxSize()
                                        ) {
                                            Text(
                                                text = it.name,
                                                modifier = Modifier
                                                    .fillMaxWidth()
                                                    .padding(start = 2.dp, bottom = 10.dp)
                                                    .align(Alignment.Start),
                                                color = Color(0xFFE0E0E0),
                                                fontFamily = FontFamily(Font(R.font.interfont)),
                                                fontSize = 16.sp,
                                                maxLines = 2,
                                                overflow = TextOverflow.Ellipsis
                                            )
                                            Row(
                                                modifier = Modifier
                                                    .padding(2.dp)
                                                    .fillMaxWidth(),
                                                horizontalArrangement = Arrangement.SpaceBetween
                                            ) {
                                                Row(verticalAlignment = Alignment.CenterVertically) {
                                                    Icon(
                                                        painter = painterResource(id = R.drawable.star),
                                                        contentDescription = null,
                                                        tint = Color.Unspecified,
                                                        modifier = Modifier.size(13.dp)
                                                    )
                                                    Text(
                                                        text = ((it.vote_average * 10).roundToInt() / 10.0).toString(),
                                                        color = Color(0xFF7091C2),
                                                        fontFamily = FontFamily(Font(R.font.robotolight)),
                                                        modifier = Modifier.padding(start = 2.dp)
                                                    )
                                                }
                                                if (it.first_air_date.isNotEmpty()) {
                                                    Text(
                                                        text = it.first_air_date.substring(0, 4),
                                                        color = Color(0xFF7091C2),
                                                        fontFamily = FontFamily(Font(R.font.robotolight)),
                                                    )
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(70.dp)
                    .padding(start = 7.dp, end = 7.dp, top = 30.dp)
                    .clip(RoundedCornerShape(20.dp))
                    .background(Color(0xFF2e2d2d)),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "No Series Found",
                    modifier = Modifier.padding(top = 10.dp, start = 15.dp, bottom = 10.dp),
                    color = Color(0xFFE0E0E0),
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}