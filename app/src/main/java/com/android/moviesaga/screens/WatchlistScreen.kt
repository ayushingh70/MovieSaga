package com.example.moviesaga.screens

import android.widget.Toast
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moviesaga.R
import com.example.moviesaga.designs.LazyRowFavouriteWatchlistSeriesDesign
import com.example.moviesaga.designs.LazyRowMoviesFavouriteWatchListDesign
import com.example.moviesaga.tmdbapidataclass.Movie.MovieDetailsData
import com.example.moviesaga.tmdbapidataclass.Series.SeriesDetailsOneData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

@Composable
fun WatchlistScreen(auth: FirebaseAuth, databaseReference: DatabaseReference, goToBackStack:()->Unit, goToMovieDescScreen:(id:Int)->Unit,goToSeriesDescScreen:(id:Int)->Unit){
    val context = LocalContext.current


    val watchMovieList = remember { mutableStateListOf<MovieDetailsData?>() }
    val watchMoviesRef = databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
        .child("watchlist").child("movie")

    LaunchedEffect(Unit) {
        watchMoviesRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                if (dataSnapshot.exists()) {
                    val favMovieDetailsList = mutableListOf<MovieDetailsData>()

                    for (movieSnapshot in dataSnapshot.children) {
                        val movieDetails = movieSnapshot.getValue(MovieDetailsData::class.java)

                        if (movieDetails != null) {
                            favMovieDetailsList.add(movieDetails)
                        }
                    }
                    watchMovieList.clear()
                    watchMovieList.addAll(favMovieDetailsList)
                }
            } else {
                Toast.makeText(context, "Failed to fetch favorite movies", Toast.LENGTH_SHORT).show()
            }
        }
    }


    val watchSeriesList = remember { mutableStateListOf<SeriesDetailsOneData?>() }

    val watchSeriesRef = databaseReference.child("users").child(auth.currentUser?.uid ?: "NA")
        .child("watchlist").child("series")

    LaunchedEffect(Unit) {
        watchSeriesRef.get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val dataSnapshot = task.result
                if (dataSnapshot.exists()) {

                    val favSeriesDetailsList = mutableListOf<SeriesDetailsOneData>()


                    for (movieSnapshot in dataSnapshot.children) {

                        val seriesDetails = movieSnapshot.getValue(SeriesDetailsOneData::class.java)


                        if (seriesDetails != null) {
                            favSeriesDetailsList.add(seriesDetails)
                        }
                    }

                    watchSeriesList.clear()
                    watchSeriesList.addAll(favSeriesDetailsList)

                }
            } else {
                Toast.makeText(context, "Failed to fetch favorite movies", Toast.LENGTH_SHORT).show()
            }
        }
    }



    Box( modifier = Modifier
        .fillMaxSize()
        .background(Color.Black) ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
                .verticalScroll(rememberScrollState())
        ) {
            Spacer(
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .background(Color.Black)
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
                                goToBackStack()
                            }
                        )
                )

            }
            LazyRowMoviesFavouriteWatchListDesign(
                watchMovieList,
                "Movies Watchlist",
                goToMovieDescScreen
            )
            LazyRowFavouriteWatchlistSeriesDesign(watchSeriesList,"Series Watchlist", goToSeriesDescScreen)
        }
    }
}

