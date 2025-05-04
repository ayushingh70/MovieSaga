package com.example.moviesaga.screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R
import com.example.moviesaga.authorization.SignUpUser
import com.example.moviesaga.tmdbMVVM.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference

@Composable
fun SignUpDetailsScreen(viewModel: ViewModel,databaseReference:DatabaseReference,auth: FirebaseAuth,signSuccessGoToLoginScreen:()->Unit) {
    val context = LocalContext.current

    var username by remember {
        mutableStateOf("")
    }
    var email by remember {
        mutableStateOf("")
    }
    var password by remember {
        mutableStateOf("")
    }
    var confirmPassword by remember {
        mutableStateOf("")
    }

    val getValidEmail = viewModel.getValidEmail.collectAsState().value



    Column {
    Box(modifier = Modifier.fillMaxSize()) {
        Image(painter = painterResource(R.drawable.image10), contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.7f),
                blendMode = BlendMode.Multiply
            ))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 50.dp)
           ) {

            Text(
                text = "Enter Your details",
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 30.dp),
                color = Color.White
            )

            OutlinedTextField(
                value = username,
                onValueChange = {
                    username = it
                },
                label = {
                    Text("Username")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = email,
                onValueChange = {
                    email = it
                    viewModel.checkEmailAddress(email)
                },
                label = {
                    Text("Email address")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = password,
                onValueChange = {
                    password = it
                },
                label = {
                    Text("password")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White

                ),

                modifier = Modifier.padding(start = 20.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 15.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            OutlinedTextField(
                value = confirmPassword,
                onValueChange = {
                    confirmPassword = it
                },
                label = {
                    Text("Confirm Password")
                },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = Color.Transparent,
                    unfocusedBorderColor = Color.Transparent,
                    focusedTextColor = Color.White,
                    unfocusedTextColor = Color.White,
                    unfocusedLabelColor = Color.White,
                    focusedLabelColor = Color.White
                ),
                modifier = Modifier.padding(start = 20.dp)
            )
            HorizontalDivider(
                modifier = Modifier.padding(start = 20.dp, end = 20.dp, bottom = 100.dp),
                thickness = 1.dp,
                color = Color.Gray
            )

            Button(
                onClick = {
                    viewModel.checkEmailAddress(email)
                    if (getValidEmail?.data?.format_valid == true &&
                        getValidEmail.data.mx_found == true &&
                        getValidEmail.data.disposable == false &&
                        getValidEmail.data.role == false &&
                        password == confirmPassword){
                        SignUpUser(
                            databaseReference,
                            auth,
                            username,
                            email,
                            password,
                            context,
                            signSuccessGoToLoginScreen
                        )
                    }
                    else {
                         if (confirmPassword != password) {
                            Toast.makeText(
                                context,
                                "confirm password not matched with password",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else if (getValidEmail?.data?.format_valid == false || !getValidEmail?.data?.mx_found!! || getValidEmail.data.disposable == true || getValidEmail.data.role == true) {
                            Toast.makeText(
                                context,
                                "Please enter a valid email address",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            Toast.makeText(
                                context,
                                "Please fill all the fields",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp, top = 0.dp)
                    .fillMaxWidth()
                    .height(60.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF265AE8),
                    contentColor = Color.White
                )

            )
            {
                Text(text = "Continue", fontSize = 16.sp)
            }
        }
    }
}
}