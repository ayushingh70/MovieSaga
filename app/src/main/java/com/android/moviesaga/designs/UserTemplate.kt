package com.example.moviesaga.designs

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.android.moviesaga.designs.UserScoreBottomSheet
import com.example.moviesaga.R
import com.example.moviesaga.tmdbMVVM.ViewModel
import com.example.moviesaga.tmdbapidataclass.Movie.MovieDetailsData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesDetailsOneData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.foundation.Canvas
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.unit.dp




@Composable
fun UserTemplate(viewModel: ViewModel,isMovie: Boolean, id:Int, auth: FirebaseAuth, databaseReference: DatabaseReference) {//movieId
    val context = LocalContext.current
    var movieDetail: MovieDetailsData? = null
    var seriesDetail: SeriesDetailsOneData? = null
    if (isMovie) {
        viewModel.getMovieDetailById(id)
        movieDetail = viewModel.getMovieDetailsById.collectAsState().value
    } else {
        viewModel.getSeriesDetailsById(id)
        seriesDetail = viewModel.getSeriesDetailsById.collectAsState().value
    }
    var favourite by remember {
        mutableStateOf(false)
    }
    var watchlist by remember {
        mutableStateOf(false)
    }
    var dataType = "movie" //series or movie, saved is series or movie
    if (!isMovie) {
        dataType = "series"
    }
    //watchlist
    val watchRef = databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
        .child("watchlist").child(dataType).child(id.toString())

    watchRef.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val snapshot = task.result
            if (snapshot.exists()) {
                // ID is present in the branch
                watchlist = true
            }
        }
    }
    var watchbg: Color = Color.Transparent
    if (watchlist) {
        watchbg = Color(0xFF103661)
    }

    //favourite
    val favRef = databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
        .child("favourite").child(dataType).child(id.toString())
    favRef.get().addOnCompleteListener { task ->
        if (task.isSuccessful) {
            val snapshot = task.result
            if (snapshot.exists()) {
                // ID is present in the branch
                favourite = true
            }
        }
    }
    var favbg: Color = Color.Transparent
    if (favourite) {
        favbg = Color(0xFF103661)
    }
    Row(
        Modifier
            .fillMaxWidth()
            .height(90.dp)
            .padding(horizontal = 20.dp)
            .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            Modifier
                .fillMaxHeight(1f)
                .padding(start = 10.dp, end = 10.dp)
                .background(favbg, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .clickable(onClick = {
                    //favourite
                    favourite = !favourite
                    // fav true means added // false means removed
                    if (favourite) { // add to data base // current user can never be null here
                        if (isMovie) {
                            val dbRef =
                                databaseReference.child("users")
                                    .child(auth.currentUser?.uid ?: "NA")
                                    .child("favourite").child(dataType).child(id.toString())
                                    .setValue(movieDetail) // just make as child named movie as id will be unique
                            //push create a auto. unique id where i can reside data
                            Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show()
                        } else {
                            val dbRef =
                                databaseReference.child("users")
                                    .child(auth.currentUser?.uid ?: "NA")
                                    .child("favourite").child(dataType).child(id.toString())
                                    .setValue(seriesDetail) // just make as child named movie as id will be unique
                            Toast.makeText(context, "Added to favourite", Toast.LENGTH_SHORT).show()
                        }
                    } else { // if favourite is false remove from database if present, obviously it will be present
                        val dbRef =
                            databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
                                .child("favourite").child(dataType)
                        dbRef.child(id.toString()).removeValue()
                        Toast.makeText(context, "Removed from favourite", Toast.LENGTH_SHORT).show()
                    }
                }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(R.drawable.favourite), contentDescription = "favourite",
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(40.dp)
            )
            Text(
                "Favourite",
                fontSize = 13.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.robotomedium))
            )
        }
        Column(
            Modifier
                .fillMaxHeight(1f)
                .padding(start = 10.dp, end = 10.dp)
                .background(watchbg, shape = RoundedCornerShape(10.dp))
                .padding(10.dp)
                .clickable(onClick = {
                    //watchlist
                    watchlist = !watchlist
                    if (watchlist) { // add to data base // current user can never be null here
                        if (isMovie) {
                            val dbRef =
                                databaseReference.child("users")
                                    .child(auth.currentUser?.uid ?: "NA")
                                    .child("watchlist").child(dataType).child(id.toString())
                                    .setValue(movieDetail) // just make as child named movie as id will be unique
                            //push create a auto. unique id where i can reside data
                            Toast.makeText(context, "Added to watchlist", Toast.LENGTH_SHORT).show()
                        } else {
                            val dbRef =
                                databaseReference.child("users")
                                    .child(auth.currentUser?.uid ?: "NA")
                                    .child("watchlist").child(dataType).child(id.toString())
                                    .setValue(seriesDetail) // just make as child named movie as id will be unique
                        }
                    } else { // if favourite is false remove from database if present, obviously it will be present
                        val dbRef =
                            databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
                                .child("watchlist").child(dataType)
                        dbRef.child(id.toString()).removeValue()
                        Toast.makeText(context, "Removed from watchlist", Toast.LENGTH_SHORT).show()

                    }
                }),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                painterResource(R.drawable.bookmark), contentDescription = "bookmark",
                modifier = Modifier
                    .padding(bottom = 5.dp)
                    .size(40.dp)
            )
            Text(
                "Watchlist",
                fontSize = 13.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.robotomedium))
            )
        }
        val scoreRaw = if (isMovie) {
            movieDetail?.vote_average ?: 0.0
        } else {
            seriesDetail?.vote_average ?: 0.0
        }

        val score = (scoreRaw * 10).toInt().coerceIn(0, 100) // Ensure score is between 0-100
        val strokeWidth = 10.dp
        val animatedSweep by animateFloatAsState(
            targetValue = (score / 100f) * 360f,
            animationSpec = tween(durationMillis = 800, easing = FastOutSlowInEasing)
        )
        val scoreText = "$score%"
        var showScoreSheet by remember { mutableStateOf(false) }

        // Gradient Ring Color
        fun getRingColor(score: Int): Color {
            return when {
                score < 40 -> Color(0xFFD32F2F) // Red
                score in 40..60 -> Color(0xFFFFA000) // Amber
                score in 61..79 -> Color(0xFF00C853).copy(alpha = 0.8f) // Light Green
                else -> Color(0xFF00E676) // Bright Green
            }
        }


        Column(
            modifier = Modifier
                .fillMaxHeight()
                .padding(horizontal = 12.dp)
                .clickable { showScoreSheet = true }
                .padding(vertical = 10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(45.dp)
                    .shadow(6.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.4f))
            ) {
                Canvas(modifier = Modifier.size(60.dp)) {
                    // Background Ring
                    drawArc(
                        color = Color.DarkGray,
                        startAngle = -90f,
                        sweepAngle = 360f,
                        useCenter = false,
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                    )

                    // Foreground Animated Ring
                    // Foreground Animated Ring
                    drawArc(
                        color = getRingColor(score),
                        startAngle = -90f,
                        sweepAngle = animatedSweep,
                        useCenter = false,
                        style = Stroke(width = strokeWidth.toPx(), cap = StrokeCap.Round)
                    )
                }
                Text(
                    text = scoreText,
                    color = Color.White,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(R.font.interbold))
                )
            }

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = "Rating",
                fontSize = 12.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.robotomedium))
            )
        }

        if (showScoreSheet) {
            UserScoreBottomSheet(
                score = score,
                voteCount = if (isMovie) movieDetail?.vote_count ?: 0 else seriesDetail?.vote_count ?: 0,
                onDismiss = { showScoreSheet = false },
                contentId = id,
                isMovie = isMovie
            )
        }
    }
}
