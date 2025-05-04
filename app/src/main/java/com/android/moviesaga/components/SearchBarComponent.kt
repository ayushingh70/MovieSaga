package com.example.moviesaga.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moviesaga.R

@Composable
fun SearchBarComponent(
    modifier: Modifier = Modifier,
    onSearch: (String) -> Unit
) {
    val text = remember { mutableStateOf("") }

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
        modifier = modifier
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
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
