package com.example.moviesaga.screens

import android.content.ActivityNotFoundException
import android.content.Context
import android.content.pm.PackageManager
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.moviesaga.R
import com.example.moviesaga.designs.BottomNavigatorDesign
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.widget.Toast
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.rotate
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import java.io.File
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
@Composable
fun SettingTabScreen(
    navController: NavController,
    goToHomeScreen: () -> Unit,
    goToMovieTabScreen: () -> Unit,
    goToSeriesTabScreen: () -> Unit,
    goToSearchScreen: () -> Unit,
    goToPopularCast: () -> Unit,
    goToProfileScreen: () -> Unit
) {
    val context = LocalContext.current
    val appVersion = getAppVersion(context)
    val deviceName = getDeviceName()
    val user = FirebaseAuth.getInstance().currentUser
    val uid = user?.uid

    LaunchedEffect(uid) {
        uid?.let {
            val dbRef = FirebaseDatabase.getInstance().getReference("users").child(uid)
            dbRef.child("deviceInfo").setValue(deviceName)
        }
    }
    var showDialog by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Black)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFF1f1f1f))
                .verticalScroll(rememberScrollState())
        ) {
            // Top Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 45.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(id = R.drawable.user),
                    contentDescription = "profile",
                    modifier = Modifier
                        .padding(start = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .padding(8.dp)
                        .clip(CircleShape)
                        .clickable { goToProfileScreen() }
                )

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        painter = painterResource(id = R.drawable.appicon),
                        contentDescription = "appicon",
                        modifier = Modifier.size(50.dp)
                    )
                    Text(
                        text = "MovieSaga",
                        fontSize = 13.sp,
                        color = Color.White,
                        fontFamily = FontFamily(Font(R.font.interbold))
                    )
                }

                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = "search",
                    modifier = Modifier
                        .padding(end = 20.dp)
                        .size(35.dp)
                        .background(Color(0xFF121212), shape = CircleShape)
                        .clip(CircleShape)
                        .padding(8.dp)
                        .clickable { goToSearchScreen() }
                )
            }

            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Settings",
                fontSize = 22.sp,
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.interbold)),
                modifier = Modifier.padding(20.dp)
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Contact Support
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(70.dp)
                    .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp))
                    .clickable {
                        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:")
                            putExtra(Intent.EXTRA_EMAIL, arrayOf("aniketom70@gmail.com"))
                            putExtra(Intent.EXTRA_SUBJECT, "Support Request")
                            putExtra(Intent.EXTRA_TEXT, "Please Explain your Issue Below-⇓\n\n")
                        }
                        emailIntent.setPackage("com.google.android.gm")

                        try {
                            context.startActivity(emailIntent)
                        } catch (e: ActivityNotFoundException) {
                            Toast.makeText(context, "Gmail app not found", Toast.LENGTH_SHORT).show()
                        }
                    },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Contact Support -> Gmail",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    modifier = Modifier.padding(20.dp)
                )
                Image(
                    painter = painterResource(id = R.drawable.back), contentDescription = null,
                    modifier = Modifier
                        .padding(end = 10.dp)
                        .rotate(180f)
                        .size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Clear Cache Section
            Row(
                modifier = Modifier.fillMaxWidth().padding(10.dp).height(70.dp)
                    .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp))
                    .clickable { showDialog = true },
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Clear Cache",
                    fontSize = 20.sp,
                    color = Color.White,
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    modifier = Modifier.padding(20.dp)
                )
                Image(
                    painterResource(id = R.drawable.back), contentDescription = null,
                    Modifier.padding(end = 10.dp).rotate(180f).size(20.dp)
                )
            }

            if (showDialog) {
                AlertDialog(
                    onDismissRequest = { showDialog = false },
                    confirmButton = {
                        TextButton(onClick = {
                            val clearedSize = clearAppCache(context)
                            Toast.makeText(context, "Cache Cleared: $clearedSize", Toast.LENGTH_SHORT).show()
                            showDialog = false
                        }) {
                            Text("Yes", color = Color.White)
                        }
                    },
                    dismissButton = {
                        TextButton(onClick = { showDialog = false }) {
                            Text("No", color = Color.White)
                        }
                    },
                    title = { Text("Clear Cache", color = Color.White) },
                    text = { Text("Are you sure you want to clear the cache?", color = Color.White) },
                    containerColor = Color(0xFF2e2d2d)
                )
            }

            Spacer(modifier = Modifier.height(20.dp))

            // App Version Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(70.dp)
                    .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "App Version: $appVersion",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // User Device Name Section
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp, end = 10.dp)
                    .height(70.dp)
                    .background(Color(0xFF2e2d2d), shape = RoundedCornerShape(10.dp)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    modifier = Modifier.padding(start = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(15.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Device: ${getDeviceName()}",
                        fontSize = 20.sp,
                        color = Color.White,
                        modifier = Modifier.padding(10.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.height(100.dp))
        }
    }

    BottomNavigatorDesign(
        navController,
        goToHomeScreen,
        goToMovieTabScreen,
        goToSeriesTabScreen,
        goToPopularCast,
        goToSettingTabScreen = {}
    )
}
// Function to clear app cache
fun clearAppCache(context: Context): String {
    return try {
        val cacheDir: File = context.cacheDir
        val sizeBefore = getFolderSize(cacheDir)
        cacheDir.deleteRecursively()
        formatSize(sizeBefore)
    } catch (e: Exception) {
        e.printStackTrace()
        "0 B"
    }
}
fun getFolderSize(dir: File): Long {
    var size: Long = 0
    dir.listFiles()?.forEach { file ->
        size += if (file.isDirectory) getFolderSize(file) else file.length()
    }
    return size
}

fun formatSize(size: Long): String {
    val kb = size / 1024.0
    val mb = kb / 1024.0
    return when {
        mb >= 1 -> String.format("%.2f MB", mb)
        kb >= 1 -> String.format("%.2f KB", kb)
        else -> "$size B"
    }
}


// Function to get app version
fun getAppVersion(context: Context): String {
    return try {
        val packageInfo = context.packageManager.getPackageInfo(context.packageName, 0)
        packageInfo.versionName ?: "Unknown"
    } catch (_: PackageManager.NameNotFoundException) {
        "Unknown"
    }
}

// Function to get User Device Name
fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER.capitalize()
    val model = Build.MODEL
    return if (model.startsWith(manufacturer, ignoreCase = true)) {
        model.capitalize()
    } else {
        "$manufacturer $model".capitalize()
    }
}

