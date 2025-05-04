package com.android.moviesaga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.moviesaga.tmdbMVVM.ViewModel
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.navigation.NavController
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.moviesaga.R
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.foundation.clickable


@Composable
fun PersonDetailScreen(
    personId: Int,
    viewModel: ViewModel,
    navController: NavController
) {
    val person by viewModel.personDetail.collectAsState()
    val credits by viewModel.personCredits.collectAsState()

    LaunchedEffect(personId) {
        viewModel.getPersonDetails(personId)
        viewModel.getPersonCredits(personId)
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        if (person == null) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        } else {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF1f1f1f))
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp, vertical = 12.dp)
            ) {
                // Back Button
                Spacer(modifier = Modifier.height(32.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.back),
                        contentDescription = "Back",
                        modifier = Modifier
                            .padding(start = 4.dp)
                            .size(18.dp)
                            .clickable { navController.popBackStack() }
                    )
                }

                Spacer(modifier = Modifier.height(8.dp))

                // 👤 Person Info
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = "https://image.tmdb.org/t/p/w185${person?.profile_path}",
                        contentDescription = person?.name,
                        modifier = Modifier
                            .size(110.dp)
                            .clip(RoundedCornerShape(14.dp)),
                        contentScale = ContentScale.Crop
                    )
                    Spacer(modifier = Modifier.width(16.dp))
                    Column {
                        Text(
                            text = person?.name ?: "",
                            fontSize = 22.sp,
                            fontFamily = FontFamily(Font(R.font.interbold)),
                            color = Color.White
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text("Department: ${person?.known_for_department ?: "N/A"}", fontSize = 14.sp, color = Color.Gray)
                        Text("Gender: ${if (person?.gender == 1) "Female" else "Male"}", fontSize = 14.sp, color = Color.Gray)
                        person?.birthday?.let { Text("Born: $it", fontSize = 14.sp, color = Color.Gray) }
                        person?.place_of_birth?.let { Text("From: $it", fontSize = 14.sp, color = Color.Gray) }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // 📘 Biography
                Text(
                    text = "Biography",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    color = Color.White
                )
                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2a2a2a)),
                    shape = RoundedCornerShape(12.dp),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Text(
                        text = person?.biography ?: "No biography available.",
                        fontSize = 14.sp,
                        color = Color.LightGray,
                        modifier = Modifier.padding(12.dp)
                    )
                }

                Spacer(modifier = Modifier.height(24.dp))

                // 🎬 Known For
                Text(
                    text = "Known For",
                    fontSize = 18.sp,
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    color = Color.White
                )
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 4.dp, vertical = 12.dp),
                    horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    val knownCredits = credits?.cast?.ifEmpty { credits?.crew } ?: emptyList()

                    items(knownCredits) { credit ->
                        Column(modifier = Modifier.width(120.dp)) {
                            AsyncImage(
                                model = "https://image.tmdb.org/t/p/w185${credit.poster_path}",
                                contentDescription = credit.title ?: credit.name,
                                modifier = Modifier
                                    .height(160.dp)
                                    .clip(RoundedCornerShape(10.dp)),
                                contentScale = ContentScale.Crop
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = credit.title ?: credit.name ?: "Untitled",
                                fontSize = 13.sp,
                                color = Color.White,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            credit.character?.let {
                                Text(text = "as $it", fontSize = 11.sp, color = Color.Gray)
                            }
                            credit.job?.let {
                                Text(text = it, fontSize = 11.sp, color = Color.Gray)
                            }
                        }
                    }
                }
                Spacer(modifier = Modifier.height(32.dp))
            }
        }
    }
}



