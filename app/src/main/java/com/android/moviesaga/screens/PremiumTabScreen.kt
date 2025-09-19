package com.android.moviesaga.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.moviesaga.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import android.widget.Toast
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.*
import java.text.SimpleDateFormat
import java.util.*
import com.airbnb.lottie.compose.*
import kotlinx.coroutines.delay


@Composable
fun PremiumTabScreen(navController: NavController, goBack: () -> Unit) {
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val database = FirebaseDatabase.getInstance().reference
    var isPremium by remember { mutableStateOf(false) }
    var validTill by remember { mutableStateOf<Long?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var showSuccessAnimation by remember { mutableStateOf(false) }
    var activateSuccess by remember { mutableStateOf(false) }
    // Check premium status on launch
    LaunchedEffect(uid) {
        if (uid != null) {
            database.child("users").child(uid).child("premium").get()
                .addOnSuccessListener { snapshot ->
                    val premiumData = snapshot.value as? Map<*, *>
                    val validUntilMillis = premiumData?.get("validTill") as? Long
                    if (validUntilMillis != null && validUntilMillis > System.currentTimeMillis()) {
                        isPremium = true
                        validTill = validUntilMillis
                    }
                    isLoading = false
                }.addOnFailureListener {
                    isLoading = false
                }
        } else {
            isLoading = false
        }
    }
    if (isLoading) {
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
            CircularProgressIndicator(color = Color.White)
        }
        return
    }
    if (activateSuccess) {
        LaunchedEffect(Unit) {
            showSuccessAnimation = true
            delay(2200)
            activateSuccess = false
            showSuccessAnimation = false
            isPremium = true
        }
    }
    if (showSuccessAnimation) {
        LaunchedEffect(Unit) {
            delay(2200) // Let the animation play for 2.5 seconds
            showSuccessAnimation = false
            isPremium = true
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1F1F1F)),
            contentAlignment = Alignment.Center
        ) {
            val composition by rememberLottieComposition(LottieCompositionSpec.Asset("success.json"))
            val progress by animateLottieCompositionAsState(
                composition = composition,
                iterations = 1
            )
            LottieAnimation(
                composition = composition,
                progress = { progress },
                modifier = Modifier.size(300.dp)
            )
        }
        return
    }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF1F1F1F))
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(
            painter = painterResource(id = R.drawable.premium),
            contentDescription = null,
            modifier = Modifier.size(120.dp)
        )
        Spacer(modifier = Modifier.height(20.dp))

        if (isPremium && validTill != null) {
            val formattedDate = remember(validTill) {
                val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
                sdf.format(Date(validTill!!))
            }
            Text(
                text = "You're a Premium User!",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.interbold)),
                color = Color(0xFFFFD700),
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = "Valid until: $formattedDate",
                fontSize = 16.sp,
                color = Color.LightGray,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(30.dp))
        } else {
            Text(
                text = "Unlock Premium Features!",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(R.font.interbold)),
                color = Color.White,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(15.dp))
            Card(
                shape = RoundedCornerShape(12.dp),
                backgroundColor = Color(0xFD8E24AA),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "✔ Play Trailers in App (No Redirect)\n✔ No Ads",
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.interfont)),
                        color = Color(0xFFE0E0E0),
                        textAlign = TextAlign.Center
                    )
                }
            }
            Spacer(modifier = Modifier.height(30.dp))
            Button(
                onClick = {
                    if (uid != null) {
                        val startDate = System.currentTimeMillis()
                        val validDate = startDate + 30L * 24 * 60 * 60 * 1000 // 30 days
                        val premiumData = mapOf(
                            "startDate" to startDate,
                            "validTill" to validDate
                        )
                        database.child("users").child(uid).child("premium")
                            .setValue(premiumData)
                            .addOnSuccessListener {
                                validTill = validDate
                                activateSuccess = true  // <- trigger animation safely
                            }
                            .addOnFailureListener {
                                Toast.makeText(context, "Failed to activate premium", Toast.LENGTH_SHORT).show()
                            }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                shape = RoundedCornerShape(12.dp),
                contentPadding = PaddingValues()
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.horizontalGradient(
                                colors = listOf(Color(0xFFFFD700), Color(0xFFFFA500))
                            ),
                            shape = RoundedCornerShape(12.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Get Premium for ₹9/month",
                        fontSize = 18.sp,
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        color = Color.Black
                    )
                }
            }
            Spacer(modifier = Modifier.height(20.dp))
        }
        Button(
            onClick = { goBack() },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Gray),
            shape = RoundedCornerShape(12.dp)
        ) {
            Text(
                text = "Go Back",
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.interbold)),
                color = Color.White
            )
        }
    }
}