package com.example.moviesaga.designs

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesaga.R

@Composable
fun BottomNavigatorDesign(navController: NavController,goToHomeScreen:()->Unit,goToMovieTabScreen:()->Unit,goToSeriesTabScreen:()->Unit, goToPopularCast:()->Unit, goToSettingTabScreen:()->Unit) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.BottomEnd
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(80.dp)
                .background(Color(0xFF212121))
                .clickable(
                    onClick = {},
                    enabled = false
                ),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceAround
        ) {
            if(navController.currentDestination?.route == "homescreen"){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color(0xFF72B2D5), shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                //home
                                goToHomeScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "home"
                    )
                    Text(
                        "Home",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                //home
                                goToHomeScreen()

                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.home),
                        contentDescription = "home"
                    )
                    Text(
                        "Home",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            if(navController.currentDestination?.route == "movietabscreen"){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color(0xFF72B2D5), shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                //moviebottomscreen
                                goToMovieTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clapperboard),
                        contentDescription = "home",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        "Movies",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                //moviebottomscreen
                                goToMovieTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.clapperboard),
                        contentDescription = "home",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        "Movies",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            if(navController.currentDestination?.route == "seriestabscreen"){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color(0xFF72B2D5), shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToSeriesTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.television),
                        contentDescription = "home",
                        modifier = Modifier
                            .size(25.dp)

                    )
                    Text(
                        "Tv Series",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToSeriesTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.television),
                        contentDescription = "home",
                        modifier = Modifier
                            .size(25.dp)

                    )
                    Text(
                        "Tv Series",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            if(navController.currentDestination?.route == "popularcast"){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color(0xFF72B2D5), shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToPopularCast()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cast),
                        contentDescription = "cast",
                        modifier = Modifier
                            .size(25.dp)

                    )
                    Text(
                        "Actor",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToPopularCast()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.cast),
                        contentDescription = "cast",
                        modifier = Modifier
                            .size(25.dp)

                    )
                    Text(
                        "Actor",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            if(navController.currentDestination?.route == "settingtabscreen"){
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color(0xFF72B2D5), shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToSettingTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "setting",
                        modifier = Modifier
                            .size(25.dp)

                    )
                    Text(
                        "Setting",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
            else {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center,
                    modifier = Modifier
                        .fillMaxHeight()
                        .padding(vertical = 10.dp)
                        .background(Color.Transparent, shape = RoundedCornerShape(10.dp))
                        .width(60.dp)
                        .clickable(
                            onClick = {
                                goToSettingTabScreen()
                            }
                        )
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.settings),
                        contentDescription = "setting",
                        modifier = Modifier
                            .size(25.dp)
                    )
                    Text(
                        "Setting",
                        color = Color(0xFFE0E0E0),
                        fontFamily = FontFamily(Font(R.font.interbold)),
                        fontSize = 10.sp,
                        modifier = Modifier.padding(top = 5.dp)
                    )
                }
            }
        }
    }
}