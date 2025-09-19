package com.example.moviesaga.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moviesaga.R
import com.example.moviesaga.designs.YouTubePlayerAllVideos
import com.example.moviesaga.tmdbMVVM.ViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun AllMovieVideosScreen(viewModel: ViewModel, id:Int, goBack:()->Unit) {
    viewModel.getMovieVideosById(id)
    val movieVideos = viewModel.getMovieVideosById.collectAsState().value
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
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp)
                    .fillMaxHeight(), verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.back), contentDescription =
                    "back",
                    modifier = Modifier
                        .padding(start = 13.dp)
                        .size(16.dp)
                        .clickable(
                            onClick = {
                                goBack()
                            }
                        )
                )
            }
        }
            if (movieVideos!= null) {
                val videos = movieVideos.results.filter {
                    it.site == "YouTube"
                }
                LazyColumn(modifier = Modifier.padding(top=100.dp)) {
                    items(videos) {
                             Column (modifier = Modifier.padding(horizontal = 10.dp)) {
                                 YouTubePlayerAllVideos(videoId = it.key)
                                 Text(text = it.name, modifier = Modifier
                                         .padding(start = 10.dp, top = 5.dp)
                                     .height(35.dp), color = Color(0xFFE0E0E0),
                                 maxLines = 2,
                                 overflow = TextOverflow.Ellipsis,
                                 fontFamily = FontFamily(Font(R.font.interbold)))
                                 Text(text = formatTimestamp(it.published_at), modifier = Modifier
                                     .padding(start = 10.dp, bottom = 5.dp)
                                     .height(30.dp),
                                     maxLines = 2,
                                     overflow = TextOverflow.Ellipsis,
                                     color = Color(0xFF5077B3))
                             }
                    }
                }
            }
        }
}