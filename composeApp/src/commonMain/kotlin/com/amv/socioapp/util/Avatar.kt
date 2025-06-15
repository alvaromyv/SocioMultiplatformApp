package com.amv.socioapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import io.github.vinceglb.filekit.PlatformFile

@Composable
fun PerfilAvatar(
    modifier: Modifier = Modifier,
    avatarUrl: String? = null,
    avatarUri: PlatformFile? = null,
    contentDescription: String? = null,
    onClick: () -> Unit = {},
    contentAlignment: Alignment = Alignment.Center,
    iconSize: Dp = 48.dp
) {
    Box(
        modifier = modifier,
        contentAlignment = contentAlignment
    ) {
        AsyncImage(
            model = avatarUri ?: (getBaseUrl() + avatarUrl),
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(iconSize)
                .clip(CircleShape)
                .clickable { onClick() }
        )
    }
}