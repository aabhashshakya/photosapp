package com.avas.photosapp.widgets

import android.net.Uri
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

/**
 * Created by Aabhash Shakya on 4/10/2024
 */
@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun PhotoWithIndex(image: Uri, index: Int) {
    Box(contentAlignment = Alignment.Center)
    {
        GlideImage(
            model = image,
            contentDescription = "",
            Modifier
                .size(30.dp)
                .aspectRatio(1f),
            contentScale = ContentScale.Crop
        )
        Text(
            index.toString(),
            style = TextStyle(
                color = Color.White,
                fontWeight = FontWeight.Bold,
                shadow = Shadow(Color.Black, offset = Offset(1f,0f))
            )
        )
    }
}