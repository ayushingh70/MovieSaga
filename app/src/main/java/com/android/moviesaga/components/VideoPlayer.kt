package com.example.moviesaga.components

import android.content.Context
import android.net.Uri
import android.view.ViewGroup
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView
import androidx.media3.common.Player
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.media3.ui.AspectRatioFrameLayout


@androidx.annotation.OptIn(androidx.media3.common.util.UnstableApi::class)
@Composable
fun VideoPlayer(videoResId: Int, onVideoEnd: () -> Unit) {
    val context = LocalContext.current
    val exoPlayer = remember {
        SimpleExoPlayer.Builder(context).build().apply {
            val mediaItem = MediaItem.fromUri(
                "android.resource://${context.packageName}/$videoResId"
            )
            setMediaItem(mediaItem)
            prepare()
            playWhenReady = true
        }
    }

    Box(modifier = Modifier.fillMaxSize()) { // Ensures video fits
        AndroidView(
            factory = { ctx ->
                PlayerView(ctx).apply {
                    player = exoPlayer
                    useController = false
                    layoutParams = ViewGroup.LayoutParams(
                        ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT
                    )
                    resizeMode = AspectRatioFrameLayout.RESIZE_MODE_ZOOM // Fits video properly
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(250.dp)
        )
    }

    DisposableEffect(exoPlayer) {
        val listener = object : Player.Listener {
            override fun onPlaybackStateChanged(state: Int) {
                if (state == Player.STATE_ENDED) {
                    onVideoEnd()
                }
            }
        }
        exoPlayer.addListener(listener)
        onDispose {
            exoPlayer.release()
        }
    }
}

