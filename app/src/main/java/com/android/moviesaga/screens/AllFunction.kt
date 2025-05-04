package com.example.moviesaga.screens

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import com.example.moviesaga.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.BlendMode

@Composable
fun Pager() {
    val imageList = listOfNotNull(
        R.drawable.image8,
        R.drawable.image5,
        R.drawable.image16,
        R.drawable.image4,
        R.drawable.image3,
        R.drawable.image10,
        R.drawable.image1,
        R.drawable.image11,
        R.drawable.image6,
        R.drawable.image7,
        R.drawable.image9,
        R.drawable.image2,
        R.drawable.image12,
        R.drawable.image13,
        R.drawable.image14,
        R.drawable.image15



    )

    val pagerState = rememberPagerState(pageCount = {
        imageList.size
    })

    LaunchedEffect(key1 = pagerState) {
        while (true) {
            kotlinx.coroutines.delay(3000L)
            val nextPage = (pagerState.currentPage + 1) % imageList.size
            pagerState.animateScrollToPage(nextPage)
        }
    }


    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = false // Disable user interaction
    ) { page ->
        Image(
            painter = painterResource(id = imageList[page]),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            contentScale = ContentScale.Crop,
            colorFilter = ColorFilter.tint(
                color = Color.Black.copy(alpha = 0.7f),
                blendMode = BlendMode.Multiply
            )
        )
    }
}


@RequiresApi(Build.VERSION_CODES.O)
fun formatTimestamp(input: String): String {
    // Parse the input timestamp
    val parsedDate = ZonedDateTime.parse(input)

    // Define the output format
    val formatter = DateTimeFormatter.ofPattern("MMM d, yyyy")

    // Format the date
    return parsedDate.format(formatter)
}

