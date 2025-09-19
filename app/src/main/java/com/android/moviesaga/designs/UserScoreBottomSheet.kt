package com.android.moviesaga.designs

import android.widget.Toast
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Divider
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import com.example.moviesaga.R
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.*
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextAlign
import com.google.firebase.database.ServerValue
import com.google.firebase.database.GenericTypeIndicator
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.platform.LocalConfiguration


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UserScoreBottomSheet(
    score: Int,
    voteCount: Int,
    contentId: Int,
    isMovie: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    val animatedScore = remember { Animatable(0f) }
    val auth = FirebaseAuth.getInstance()
    val user = auth.currentUser
    val database = FirebaseDatabase.getInstance().reference
    val uid = user?.uid
    var userRating by remember { mutableStateOf(0) }
    var ratingExists by remember { mutableStateOf(false) }
    var distributionState by remember { mutableStateOf<Map<Int, Int>>(emptyMap()) }
    var historyState by remember { mutableStateOf<List<Pair<Long, Int>>>(emptyList()) }
    var isDistributionLoading by remember { mutableStateOf(true) }
    var isHistoryLoading by remember { mutableStateOf(true) }
    val typePath = if (isMovie) "movie" else "series"

    val configuration = LocalConfiguration.current
    val screenWidth = configuration.screenWidthDp.dp
    val itemCount = 11
    val spacing = 11.dp

// Calculate dynamic box size (subtract spacing and padding)
    val totalSpacing = spacing * (itemCount - 1) + 30.dp  // 16dp padding on each side
    val availableWidth = screenWidth - totalSpacing
    val dynamicSize = availableWidth / itemCount


    LaunchedEffect(Unit) {
        animatedScore.animateTo(score / 100f)

        // Check if the user already submitted a rating
        uid?.let { userId ->
            database.child(typePath)
                .child(contentId.toString())
                .child("ratings")
                .child(userId)
                .get()
                .addOnSuccessListener { snapshot ->
                    val ratingMap = snapshot.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                    val value = ratingMap?.get("value") as? Long
                    if (value != null) {
                        userRating = value.toInt()
                        ratingExists = true
                    }
                }
        }

        // Calculate average score (optional, for future display)
        database.child(typePath)
            .child(contentId.toString())
            .child("ratings")
            .get()
            .addOnSuccessListener { snapshot ->
                var total = 0
                var count = 0
                for (child in snapshot.children) {
                    val ratingMap = child.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                    val value = ratingMap?.get("value") as? Long
                    if (value != null) {
                        total += value.toInt()
                        count++
                    }
                }
                if (count > 0) {
                    val average = total.toFloat() / count
                    // You can use this `average` for dynamic score update if needed
                }
            }

        // Score Distribution
        val scoreDistribution = mutableMapOf<Int, Int>()
        database.child(typePath)
            .child(contentId.toString())
            .child("ratings")
            .get()
            .addOnSuccessListener { snapshot ->
                for (child in snapshot.children) {
                    val ratingMap = child.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                    val value = ratingMap?.get("value") as? Long
                    if (value != null) {
                        val score = value.toInt()
                        scoreDistribution[score] = (scoreDistribution[score] ?: 0) + 1
                    }
                }
                distributionState = scoreDistribution.toSortedMap()
                isDistributionLoading = false
            }

        // Score History
        val historyList = mutableListOf<Pair<Long, Int>>()
        database.child(typePath)
            .child(contentId.toString())
            .child("ratings")
            .get()
            .addOnSuccessListener { snapshot ->
                for (child in snapshot.children) {
                    val ratingMap = child.getValue(object : GenericTypeIndicator<Map<String, Any>>() {})
                    val value = ratingMap?.get("value") as? Long
                    val timestamp = ratingMap?.get("timestamp") as? Long
                    if (value != null && timestamp != null) {
                        historyList.add(Pair(timestamp, value.toInt()))
                    }
                }
                historyState = historyList.sortedBy { it.first }
                isHistoryLoading = false
            }
    }

    val ringColor = when {
        score in  1..10 -> Color(0xFFFF0000)
        score in 11..20 -> Color(0xFFFF3300)
        score in 21..30 -> Color(0xFFFF6600)
        score in 31..40 -> Color(0xFFFF9900)
        score in 41..50 -> Color(0xFFFFCC00)
        score in 51..60 -> Color(0xFFCCFF00)
        score in 61..70 -> Color(0xFF99FF00)
        score in 71..80 -> Color(0xFF66CC00)
        score in 81..90 -> Color(0xFF339900)
        score in 91..100 -> Color(0xFF007F00)
        else -> Color(0xFF66CC00)
    }

    val emoji = when {
        score in 1..10 -> "😞"
        score in 11..20 -> "😟"
        score in 21..30 -> "🙁"
        score in 31..40 -> "😐"
        score in 41..50 -> "🙂"
        score in 51..60 -> "😊"
        score in 61..70 -> "😃"
        score in 71..80 -> "🤩"
        score in 81..90 -> "😍"
        score in 91..100 -> "🌟🌟🌟🌟🌟"
        else -> "Not Released Yet"
    }

    val emojiScale by animateFloatAsState(
        targetValue = 1.2f,
        animationSpec = tween(300),
        label = "Emoji Scale"
    )

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        containerColor = Color(0xFF1f1f1f),
        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
    ) {
        Column(modifier = Modifier.padding(20.dp)) {
            Text(
                "User Score Breakdown",
                color = Color.White,
                fontFamily = FontFamily(Font(R.font.interbold)),
                fontSize = 22.sp
            )
            Spacer(modifier = Modifier.height(16.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(contentAlignment = Alignment.Center) {
                    Canvas(modifier = Modifier.size(70.dp)) {
                        drawArc(
                            color = Color.DarkGray,
                            startAngle = -90f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                        )
                        drawArc(
                            color = ringColor,
                            startAngle = -90f,
                            sweepAngle = animatedScore.value * 360f,
                            useCenter = false,
                            style = Stroke(width = 10.dp.toPx(), cap = StrokeCap.Round)
                        )
                    }
                    Text(
                        "$score%",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontFamily = FontFamily(Font(R.font.interbold))
                    )
                }
                Spacer(modifier = Modifier.width(20.dp))
                Column {
                    Text("$voteCount Ratings", color = Color.Gray, fontSize = 14.sp)
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Sentiment >   ", color = Color.LightGray, fontSize = 14.sp)
                        Text(emoji, fontSize = 18.sp, modifier = Modifier.scale(emojiScale))
                    }
                }
            }

            Spacer(modifier = Modifier.height(28.dp))
            Divider(color = Color.DarkGray, thickness = 1.dp)
            Spacer(modifier = Modifier.height(20.dp))


// Score Distribution Title
            Text(
                "Score Distribution",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.interbold))
            )

            Spacer(modifier = Modifier.height(10.dp))

// Score Distribution Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF2a2a2a), RoundedCornerShape(10.dp))
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                if (distributionState.isNotEmpty()) {
                    Row(
                        modifier = Modifier.fillMaxSize(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.Bottom
                    ) {
                        (1..10).forEach { score ->
                            val count = distributionState[score] ?: 0
                            val max = distributionState.values.maxOrNull() ?: 1
                            val rawHeight = (count.toFloat() / max) * 70f
                            val animatedHeight by animateDpAsState(
                                targetValue = rawHeight.dp.coerceAtLeast(2.dp),
                                animationSpec = tween(durationMillis = 500),
                                label = "BarHeightAnimation"
                            )

                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Bottom
                            ) {
                                AnimatedVisibility(visible = count > 0) {
                                    Text(
                                        text = "$count",
                                        fontSize = 10.sp,
                                        color = Color.LightGray,
                                        modifier = Modifier.padding(bottom = 2.dp)
                                    )
                                }
                                Box(
                                    modifier = Modifier
                                        .width(12.dp)
                                        .height(animatedHeight)
                                        .clip(RoundedCornerShape(topStart = 4.dp, topEnd = 4.dp))
                                        .background(Color(0xFF00C853))
                                )
                                Spacer(modifier = Modifier.height(4.dp))
                                Text("$score", fontSize = 11.sp, color = Color.White)
                            }
                        }
                    }
                } else {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(70.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

// Score History Title
            Text(
                "Score History",
                color = Color.White,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(R.font.interbold))
            )

            Spacer(modifier = Modifier.height(10.dp))

// Score History Chart
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(100.dp)
                    .background(Color(0xFF2a2a2a), RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (historyState.isNotEmpty()) {
                    val maxScore = 10
                    val minTime = historyState.first().first
                    val maxTime = historyState.last().first
                    val timeSpan = (maxTime - minTime).takeIf { it > 0 } ?: 1

                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val width = size.width
                        val height = size.height

                        val points = historyState.map { (timestamp, score) ->
                            val x = ((timestamp - minTime).toFloat() / timeSpan) * width
                            val y = height - (score.toFloat() / maxScore) * height
                            Offset(x, y)
                        }

                        // Shaded area
                        val path = Path().apply {
                            if (points.isNotEmpty()) {
                                moveTo(points.first().x, height)
                                points.forEach { lineTo(it.x, it.y) }
                                lineTo(points.last().x, height)
                                close()
                            }
                        }
                        drawPath(path, color = Color(0xFF00C853).copy(alpha = 0.3f))

                        // Line chart
                        for (i in 0 until points.lastIndex) {
                            drawLine(
                                color = Color(0xFF00C853),
                                start = points[i],
                                end = points[i + 1],
                                strokeWidth = 3f
                            )
                        }

                        // Circles on points
                        points.forEach { point ->
                            drawCircle(color = Color.White, radius = 4f, center = point)
                        }
                    }
                } else {
                    ShimmerBox(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(100.dp)
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(10.dp))
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
            Divider(color = Color.DarkGray, thickness = 2.dp)
            Spacer(modifier = Modifier.height(15.dp))
            Text(
                text = "Give Your Rating",
                color = Color.White,
                fontSize = 18.sp,
                fontFamily = FontFamily(Font(R.font.interbold))
            )

            Spacer(modifier = Modifier.height(8.dp))

            if (!ratingExists) {
                val ratingColor = when (userRating) {
                    1 -> Color(0xFFFF0000)   // Red
                    2 -> Color(0xFFFF3300)   // Reddish-Orange
                    3 -> Color(0xFFFF6600)   // Orange
                    4 -> Color(0xFFFF9900)   // Yellow-Orange
                    5 -> Color(0xFFFFCC00)   // Warm Yellow
                    6 -> Color(0xFFCCFF00)   // Lime-Yellow
                    7 -> Color(0xFF99FF00)   // Lime Green
                    8 -> Color(0xFF66CC00)   // Fresh Green
                    9 -> Color(0xFF339900)   // Standard Green
                    10 -> Color(0xFF007F00)  // Dark Green
                    else -> Color.Gray       // Default for 0 or invalid
                }

            Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    (1..10).forEach { rating ->
                        val isSelected = userRating == rating
                        Box(
                            modifier = Modifier
                                .size(dynamicSize)
                                .clip(CircleShape)
                                .background(if (isSelected) ratingColor else Color.DarkGray)
                                .clickable { userRating = rating }
                                .border(
                                    width = if (isSelected) 2.dp else 0.dp,
                                    color = Color.White.copy(alpha = 0.3f),
                                    shape = CircleShape
                                ),
                            contentAlignment = Alignment.Center
                        ) {
                            Text(
                                text = rating.toString(),
                                color = Color.White,
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                Button(
                    onClick = {
                        if (userRating > 0 && uid != null) {
                            val ratingData = mapOf(
                                "value" to userRating,
                                "timestamp" to ServerValue.TIMESTAMP
                            )
                            database.child(typePath)
                                .child(contentId.toString())
                                .child("ratings")
                                .child(uid)
                                .setValue(ratingData)
                                .addOnSuccessListener {
                                    ratingExists = true
                                    Toast.makeText(context, "Rating submitted!", Toast.LENGTH_SHORT).show()
                                }

                                .addOnFailureListener {
                                    Toast.makeText(
                                        context,
                                        "Failed to submit rating",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                }
                        }
                    },
                    enabled = userRating > 0,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    shape = RoundedCornerShape(16.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = if (userRating > 0) ratingColor else Color.Gray
                    )
                ) {
                    Text(
                        "Submit Rating",
                        color = Color.Black,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }

            } else {
                Spacer(modifier = Modifier.height(10.dp))

                val scaleAnim by animateFloatAsState(
                    targetValue = 1f,
                    animationSpec = tween(durationMillis = 500),
                    label = "Rating Scale"
                )

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .scale(scaleAnim),
                    shape = RoundedCornerShape(12.dp),
                    colors = CardDefaults.cardColors(containerColor = Color(0xFF2a2a2a)),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                ) {
                    Column(
                        modifier = Modifier
                            .padding(vertical = 16.dp)
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = "Star Icon",
                                tint = Color(0xFF00C853),
                                modifier = Modifier.size(20.dp)
                            )
                            Spacer(modifier = Modifier.width(5.dp))
                            Text(
                                text = "Your Rating: $userRating / 10",
                                color = Color(0xFF00C853),
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(6.dp))

                        Text(
                            text = "Thank you for your feedback!",
                            color = Color.LightGray,
                            fontSize = 13.sp,
                            fontStyle = FontStyle.Italic
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}