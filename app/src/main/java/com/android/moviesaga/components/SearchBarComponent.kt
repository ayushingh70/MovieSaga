package com.example.moviesaga.components

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.drawscope.Stroke

@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val text = remember { mutableStateOf("") }

    val infiniteTransition = rememberInfiniteTransition(label = "")
    val animatedOffset by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 8000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = ""
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp) // match TextField height
    ) {
        // Draw animated border exactly behind the text field
        Canvas(
            modifier = Modifier
                .matchParentSize()
        ) {
            drawRoundRect(
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color(0xFF00B4FF).copy(alpha = 0.7f),
                        Color(0xFF00C853).copy(alpha = 0.7f),
                        Color(0xFFFF9800).copy(alpha = 0.7f),
                        Color(0xFF00B4FF).copy(alpha = 0.7f),
                    ),
                    start = Offset(animatedOffset, 0f),
                    end = Offset(0f, animatedOffset)
                ),
                cornerRadius = CornerRadius(10.dp.toPx(), 10.dp.toPx()),
                style = Stroke(width = 1.5.dp.toPx())
            )
        }

        OutlinedTextField(
            value = text.value,
            onValueChange = { text.value = it },
            label = {
                Text(
                    "Search",
                    fontFamily = FontFamily(Font(R.font.interbold)),
                    fontSize = 13.sp
                )
            },
            modifier = Modifier
                .fillMaxSize(), // match Box and Canvas
            shape = RoundedCornerShape(10.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.White,
                unfocusedTextColor = Color.White,
                unfocusedBorderColor = Color.Transparent,
                focusedBorderColor = Color.Transparent
            ),
            maxLines = 1,
            singleLine = true,
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Search
            ),
            keyboardActions = KeyboardActions(
                onSearch = {
                    onSearch(text.value)
                }
            )
        )
    }

    Spacer(modifier = Modifier.height(10.dp))

    Button(
        onClick = {
            onSearch(text.value)
        },
        modifier = Modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = ButtonDefaults.buttonColors(
            contentColor = Color.White,
            containerColor = Color(0xFF6650a4)
        )
    ) {
        Text(
            "Search",
            fontFamily = FontFamily(Font(R.font.interbold)),
            fontSize = 14.sp
        )
    }
}
