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
import androidx.navigation.NavController
import com.example.moviesaga.R


@Composable
fun PremiumTabScreen(navController: NavController, goBack: () -> Unit) {
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
