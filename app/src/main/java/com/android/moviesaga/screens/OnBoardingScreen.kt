package com.example.moviesaga.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R

@SuppressLint("SuspiciousIndentation")
@Composable
fun OnBoardingScreen(
    goToLogInScreen: () -> Unit,
    goToSignUpScreen: () -> Unit,
    goToHomeScreen: () -> Unit
) {
    Box(Modifier
        .fillMaxSize()
        .background(Color.Transparent)) {
        Pager()
        Text(
            "Skip", fontFamily = FontFamily(Font(R.font.robotolight)),
            fontSize = 15.sp,
            color = Color.White,
            modifier = Modifier
                .align(
                    Alignment.TopEnd
                )
                .padding(top = 90.dp, end = 30.dp)
                .clickable(
                    onClick = goToHomeScreen//withoutlogin
                ),
            textAlign = TextAlign.End
        )
        Column(Modifier
            .fillMaxSize(),verticalArrangement = Arrangement.Center) {
            Text(
                "WELCOME TO\nMovieSaga!",
                fontFamily = FontFamily(Font(R.font.interbold)),
                fontSize = 30.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 30.dp)
            )
            Text(
                "Your ultimate destination for discovering movies and series!",
                fontFamily = FontFamily(Font(R.font.robotomedium)),
                fontSize = 16.sp,
                color = Color.White,
                modifier = Modifier.padding(start = 30.dp,top=5.dp)
            )
        }
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(10.dp),
            contentAlignment = Alignment.BottomEnd
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp)
                    .border(width = 2.dp, Color.Black, RoundedCornerShape(10.dp)),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Button(
                    onClick = {
                        goToSignUpScreen()
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 15.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFF1b059c),
                        contentColor = Color.White
                    )
                ) {
                    Text(text = "Register", fontSize = 14.sp)
                }

                Button(
                    onClick = {
                        goToLogInScreen()
                    },
                    shape = RoundedCornerShape(10.dp),
                    modifier = Modifier
                        .padding(start = 20.dp, end = 20.dp, top = 25.dp)
                        .fillMaxWidth()
                        .height(60.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color(0xFF1b059c),
                        containerColor = Color.White
                    ),
                    border = BorderStroke(width = 0.2.dp, color = Color.Gray)
                ) {
                    Text(text = "Log in")
                }
            }
        }
    }
}