package com.example.moviesaga.screens

import android.content.Context
import coil.compose.rememberAsyncImagePainter
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.google.firebase.auth.FirebaseAuth
import com.example.moviesaga.R
import com.google.firebase.database.DatabaseReference
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.ui.graphics.Brush


@Composable
fun Profile(
    databaseReference: DatabaseReference,
    context: Context,
    auth: FirebaseAuth,
    goToOnBoardingScreen: () -> Unit,
    goToBackStack: () -> Unit,
    goToFavouriteScreen: () -> Unit,
    goToWatchlistScreen: () -> Unit,
    goToPremiumTabScreen: () -> Unit
) {
    var name by remember { mutableStateOf("") }
    var alertBox by remember { mutableStateOf(false) }

    val user = auth.currentUser

    if (user == null) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
                .padding(20.dp)
                .statusBarsPadding(),
            verticalArrangement = Arrangement.Center,

            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                "Login/SignUp to TMDb account",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(R.font.interbold)),
                color = Color(0xFFE0E0E0),
                modifier = Modifier.padding(bottom = 20.dp)
            )

            ProfileFeatureItem(icon = R.drawable.star, text = "Rate Movies and TV shows")
            ProfileFeatureItem(icon = R.drawable.bookmark, text = "Manage your watchlist")
            ProfileFeatureItem(icon = R.drawable.premium, text = "Get Premium Features")
            ProfileFeatureItem(icon = R.drawable.list, text = "Create your list of movies and series")

            Spacer(modifier = Modifier.height(40.dp))

            Button(
                onClick = { goToOnBoardingScreen() },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF9B59B6))
            ) {
                Text(
                    "Login / Sign Up",
                    color = Color.White,
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(R.font.interbold))
                )
            }
        }
    } else {
        val dbRef = databaseReference.child("users").child(auth.currentUser!!.uid)
        LaunchedEffect(Unit) {
            dbRef.child("username").get().addOnSuccessListener { snapshot ->
                name = snapshot.getValue(String::class.java) ?: user.displayName ?: "Unknown User"
            }.addOnFailureListener {
                name = user.displayName ?: "Unknown User"
            }
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(Color(0xFF111111), Color(0xFF222222))
                    )
                )
                .verticalScroll(rememberScrollState())
                .statusBarsPadding()
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.back),
                    contentDescription = "Back",
                    tint = Color.White,
                    modifier = Modifier
                        .size(20.dp)
                        .clickable { goToBackStack() }
                )
                Spacer(modifier = Modifier.width(16.dp))
                Text(
                    text = "Your Profile",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.interbold))
                )
            }

            // Profile Info
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
                    .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(18.dp))
                    .padding(16.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val profilePicUrl = user.photoUrl
                if (profilePicUrl != null) {
                    Image(
                        painter = rememberAsyncImagePainter(profilePicUrl),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(id = R.drawable.appicon),
                        contentDescription = null,
                        modifier = Modifier
                            .size(70.dp)
                            .clip(CircleShape)
                    )
                }

                Spacer(modifier = Modifier.width(16.dp))
                Column {
                    Text(
                        text = "Hello,",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.interfont)),
                        color = Color.LightGray
                    )
                    Text(
                        text = name,
                        fontSize = 22.sp,
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        color = Color.White
                    )
                }
            }

            // Options
            Spacer(modifier = Modifier.height(10.dp))
            ProfileOptionItem(icon = R.drawable.favourite, text = "Favourite", onClick = goToFavouriteScreen)
            ProfileOptionItem(icon = R.drawable.bookmark, text = "Watchlist", onClick = goToWatchlistScreen)
            ProfileOptionItem(icon = R.drawable.premium, text = "Premium", onClick = goToPremiumTabScreen)

            Spacer(modifier = Modifier.height(340.dp))

            Button(
                onClick = { alertBox = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 20.dp)
                    .height(65.dp),
                shape = RoundedCornerShape(40),
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFC93E3E))
            ) {
                Text(
                    "Log Out",
                    color = Color.White,
                    fontSize = 19.sp,
                    fontFamily = FontFamily(Font(R.font.interbold))
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
        }

        if (alertBox) {
            AlertDialog(
                onDismissRequest = { alertBox = false },
                confirmButton = {
                    TextButton(onClick = {
                        auth.signOut()
                        goToOnBoardingScreen()
                    }) {
                        Text("Yes", color = Color.White)
                    }
                },
                dismissButton = {
                    TextButton(onClick = { alertBox = false }) {
                        Text("No", color = Color.White)
                    }
                },
                title = { Text("Confirm Logout?", color = Color.White) },
                text = { Text("Are you sure you want to logout?", color = Color.White) },
                containerColor = Color(0xFF2e2d2d)
            )
        }
    }
}

// Reusable UI section
@Composable
fun ProfileFeatureItem(icon: Int, text: String) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
            .height(70.dp)
            .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = painterResource(id = icon),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 20.dp)
                .size(30.dp)
        )
        Spacer(modifier = Modifier.width(16.dp))
        Text(
            text,
            color = Color(0xFFE0E0E0),
            fontSize = 16.sp,
            fontFamily = FontFamily(Font(R.font.interfont))
        )
    }
}

@Composable
fun ProfileOptionItem(icon: Int, text: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 20.dp, vertical = 8.dp)
            .height(70.dp)
            .clip(RoundedCornerShape(10.dp))
            .background(Color(0xFF2e2d2d))
            .clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start = 16.dp)
        ) {
            Image(
                painter = painterResource(id = icon),
                contentDescription = null,
                modifier = Modifier.size(30.dp)
            )
            Spacer(modifier = Modifier.width(12.dp))
            Text(
                text,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.interbold)),
                color = Color.White
            )
        }

        Icon(
            painter = painterResource(id = R.drawable.back),
            contentDescription = null,
            tint = Color.White,
            modifier = Modifier
                .padding(end = 16.dp)
                .size(20.dp)
                .rotate(180f)
        )
    }
}