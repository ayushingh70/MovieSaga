package com.example.moviesaga.designs

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun DotPageIndicator(totalDots: Int, selectedIndex: Int) {
    Row(
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        for (index in 0 until totalDots) {
            val color = if (index == selectedIndex) Color.White else Color.Gray
            Box(
                modifier = Modifier
                    .padding(horizontal = 4.dp)
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(color)
            )
        }
    }
}