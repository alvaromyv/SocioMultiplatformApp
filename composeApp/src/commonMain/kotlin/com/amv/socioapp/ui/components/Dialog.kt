package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

@Composable
fun MiDialogoConfirmacion(
    onRechazarRequest: () -> Unit,
    onConfirmar: () -> Unit,
    titulo: String,
    texto: String,
    icon: ImageVector,
) {
    AlertDialog(
        icon = {
            Icon(
                imageVector = icon,
                modifier = Modifier.size(48.dp),
                tint = MaterialTheme.colorScheme.primary,
                contentDescription = titulo
            )
        },
        title = {
            Text(text = titulo)
        },
        text = {
            Text(text = texto)
        },
        onDismissRequest = {
            onRechazarRequest
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirmar()
                }
            ) {
                Text("Aceptar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onRechazarRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}