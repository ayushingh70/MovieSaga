package com.example.moviesaga.screens

import android.content.Intent
import android.widget.Toast
import androidx.activity.compose.ManagedActivityResultLauncher
import androidx.activity.result.ActivityResult
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.GoogleSignInUtils
import com.example.moviesaga.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.CoroutineScope

@Composable
fun SignUpScreen(databaseReference: DatabaseReference,auth: FirebaseAuth,goToHomeScreen:()->Unit,goToLoginScreen: () -> Unit, goToSignUpDetails: () -> Unit,scope: CoroutineScope,launcher: ManagedActivityResultLauncher<Intent, ActivityResult>?) {
    val context = LocalContext.current
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(id = R.drawable.image12),contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.7f),
                blendMode = BlendMode.Multiply
            ))
        Column(
            modifier = Modifier.fillMaxSize().padding(bottom=50.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Bottom
        ) {

            Text(
                text = "Welcome\nSign up to continue",
                fontFamily = FontFamily(Font(R.font.interbold)),
                fontSize = 26.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 10.dp, bottom = 50.dp),
                color = Color.White
            )

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, bottom = 15.dp)
                    .height(60.dp)
                    .clickable(onClick = {

                    }),
                colors = CardDefaults.cardColors(Color(0xFFDBDEDE)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxSize().clickable(
                        onClick = {
                            GoogleSignInUtils.doGoogleSignIn(
                                context = context,
                                scope = scope,
                                launcher = launcher,
                                login = {
                                    if(databaseReference.child("users").child(auth.currentUser!!.uid).get().isSuccessful){
                                        Toast.makeText(context, "Welcome Back",Toast.LENGTH_SHORT).show()
                                    }
                                    else{
                                        databaseReference.child("users").child(auth.currentUser!!.uid).child("username").setValue(auth.currentUser!!.displayName)
                                        Toast.makeText(context, "New User",Toast.LENGTH_SHORT).show()
                                    }
                                    goToHomeScreen()
                                }
                            )
                        }
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.google),
                        contentDescription = null,
                        modifier = Modifier.size(25.dp),
                        alignment = Alignment.CenterStart
                    )
                    Text(
                        text = "Continue with Google",
                        modifier = Modifier.padding(start = 10.dp),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Text(text = "OR", color = Color.Gray)

            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 30.dp, end = 30.dp, top = 15.dp, bottom = 15.dp)
                    .height(60.dp)
                    .clickable(onClick = {

                    }),
                colors = CardDefaults.cardColors(Color(0xFFDBDEDE)),
                shape = RoundedCornerShape(5.dp)
            ) {
                Box(modifier = Modifier.clickable(onClick = {
                    goToSignUpDetails()
                })) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "Sign up with Email",
                            modifier = Modifier.padding(start = 10.dp),
                            fontSize = 16.sp,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            Text(
                text = "By signing up you are agreed with our\n" +
                        "friendly terms and condition.",
                textAlign = TextAlign.Center,
                fontSize = 13.sp,
                color = Color.Gray
            )

            Text(
                text = "Already have an account?",
                textAlign = TextAlign.Center,
                fontSize = 15.sp,
                color = Color.Gray,
                modifier = Modifier.padding(top = 50.dp, bottom = 15.dp)
            )


            Text(
                text = "Sign in",
                color = Color(0xFF265AE8),
                fontWeight = FontWeight.Medium,
                modifier = Modifier.clickable(onClick = {
                    goToLoginScreen()
                })
            )
        }
    }
}