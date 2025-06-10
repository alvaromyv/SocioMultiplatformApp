package com.amv.socioapp.util

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun PerfilAvatar(
    avatarLink: String,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    esUri: Boolean = false,
    onClick: () -> Unit = {},
    iconSize: Dp = 48.dp
) {
    AsyncImage(
        model = if(esUri) avatarLink else getBaseUrl()+avatarLink,
        contentDescription = contentDescription,
        contentScale = ContentScale.Crop,
        modifier = modifier
            .size(iconSize)
            .clip(CircleShape)
            .clickable { onClick }
    )
}