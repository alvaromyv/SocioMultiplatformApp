package com.amv.socioapp.ui.events

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Error
import androidx.compose.material.icons.filled.Info
import androidx.compose.material3.Icon
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarVisuals
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

data class SnackbarEvent(
    val message: String,
    val action: SnackbarAction ?= null,
    val type: SnackbarType = SnackbarType.INFO
)

data class SnackbarAction(
    val name: String,
    val action: suspend () -> Unit
)

object SnackbarController {
    private val _events = Channel<SnackbarEvent>()
    val events = _events.receiveAsFlow()

    suspend fun sendEvent(event: SnackbarEvent) {
        _events.send(event)
    }
}

data class  SnackbarVisualsCustom(
    override val message: String,
    override val actionLabel: String? = null,
    override val withDismissAction: Boolean = true,
    override val duration: SnackbarDuration = if (actionLabel == null) SnackbarDuration.Short else SnackbarDuration.Indefinite,
    val contentAlignment: Alignment = Alignment.BottomCenter,
    val type:SnackbarType = SnackbarType.ERROR,
    val onClickAction:()->Unit = {}
) : SnackbarVisuals

enum class SnackbarType {
    SUCCESS, ERROR, INFO
}

@Composable
fun MiCustomSnackbar(snackbarHostState: SnackbarHostState) {
    SnackbarHost(hostState = snackbarHostState) { snackbarData ->
        val visuals = snackbarData.visuals as? SnackbarVisualsCustom
        val type = visuals?.type ?: SnackbarType.INFO
        val message = visuals?.message ?: snackbarData.visuals.message

        val backgroundColor: Color
        val icon: ImageVector

        when (type) {
            SnackbarType.SUCCESS -> {
                backgroundColor = Color(0xFF4CAF50)
                icon = Icons.Filled.CheckCircle
            }
            SnackbarType.ERROR -> {
                backgroundColor = Color(0xFFF44336)
                icon = Icons.Filled.Error
            }
            SnackbarType.INFO -> {
                backgroundColor = Color(0xFF2196F3)
                icon = Icons.Filled.Info
            }
        }

        Snackbar(
            containerColor = backgroundColor,
            contentColor = Color.White,
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(imageVector = icon, contentDescription = message, tint = Color.White)
                Spacer(modifier = Modifier.width(8.dp))
                Text(text = message)
            }
        }
    }
}