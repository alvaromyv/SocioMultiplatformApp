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
import com.amv.socioapp.network.model.ResponseError
import com.amv.socioapp.network.model.ResponseSuccess
import com.amv.socioapp.network.model.SimpleResponse
import com.amv.socioapp.network.repository.UsuariosRepository
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import io.ktor.client.request.forms.MultiPartFormDataContent
import io.ktor.client.request.forms.formData
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import kotlinx.serialization.SerializationException

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

suspend fun subirAvatar(
    usuariosRepository: UsuariosRepository,
    id: Int,
    avatar: PlatformFile
): SimpleResponse? {
    val avatarBytes = avatar.readBytes()
    val parts = formData {
        append("avatar", avatarBytes, Headers.build {
            append(HttpHeaders.ContentType, "image/png")
            append(HttpHeaders.ContentDisposition, "filename=${avatar.name}")
        })
    }

    return when (val response = usuariosRepository.subirAvatar(id, MultiPartFormDataContent(parts))) {
        is ResponseSuccess -> response.data as? SimpleResponse
        is ResponseError -> throw Exception(response.error.message)
        else -> throw SerializationException()
    }
}