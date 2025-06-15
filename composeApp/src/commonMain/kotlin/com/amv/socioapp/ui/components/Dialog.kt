package com.amv.socioapp.ui.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatListNumbered
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import com.amv.socioapp.data.AjustesManager
import com.amv.socioapp.data.AppLanguage
import com.amv.socioapp.data.AppThemeMode
import com.amv.socioapp.util.responsiveFillMaxWidth

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

@Composable
fun AjustesDialog(
    onDismiss: () -> Unit,
    onCerrarSesion: () -> Unit,
    onReasignarNumeracion: () -> Unit
) {
    var mostrarDialogoReasignacion by remember { mutableStateOf(false) }

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.responsiveFillMaxWidth(),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = "Ajustes",
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                AjustesDialogSectionTitle(text = "Tema")
                Column(Modifier.selectableGroup()) {
                    AjustesDialogEscogerRow(
                        text = "Mismo que en el dispositivo",
                        selected = AjustesManager.theme == AppThemeMode.SYSTEM,
                        onClick = { AjustesManager.onChangeTheme(AppThemeMode.SYSTEM) },
                    )
                    AjustesDialogEscogerRow(
                        text = "Claro",
                        selected = AjustesManager.theme == AppThemeMode.LIGHT,
                        onClick = { AjustesManager.onChangeTheme(AppThemeMode.LIGHT)  },
                    )
                    AjustesDialogEscogerRow(
                        text = "Oscuro",
                        selected = AjustesManager.theme == AppThemeMode.DARK,
                        onClick = { AjustesManager.onChangeTheme(AppThemeMode.DARK) },
                    )
                }
                AjustesDialogSectionTitle(text = "Idioma")
                Column(Modifier.selectableGroup()) {
                    AjustesDialogEscogerRow(
                        text = "Inglés",
                        selected = AjustesManager.language == AppLanguage.ENGLISH,
                        onClick = { AjustesManager.onLanguageChange(AppLanguage.ENGLISH)  },
                    )
                    AjustesDialogEscogerRow(
                        text = "Español",
                        selected = AjustesManager.language == AppLanguage.SPANISH,
                        onClick = { AjustesManager.onLanguageChange(AppLanguage.SPANISH)  },
                    )
                }
                HorizontalDivider(Modifier.padding(top = 8.dp, bottom = 24.dp))
                MiButton(
                    accion = "Reasignar numeración socios",
                    onClick = { mostrarDialogoReasignacion = true },
                    modifier = Modifier.fillMaxWidth()
                )
                MiButton(
                    accion = "Cerrar sesión",
                    onClick = onCerrarSesion,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            MiTextButton(
                onClick = onDismiss,
                modifier = Modifier.padding(horizontal = 8.dp),
            ) {
                Text(
                    text = "OK",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary,
                )
            }
        }
    )

    if (mostrarDialogoReasignacion) {
        MiDialogoConfirmacion(
            onRechazarRequest = { mostrarDialogoReasignacion = false },
            onConfirmar = {
                onReasignarNumeracion()
                mostrarDialogoReasignacion = false
            },
            icon = Icons.Filled.FormatListNumbered,
            titulo = "Reasignar numeración",
            texto = "¿Estás seguro de que quieres reasignar la numeración de los socios?"
        )
    }
}

@Composable
fun AjustesDialogEscogerRow(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
) {
    Row(
        Modifier
            .fillMaxWidth()
            .selectable(
                selected = selected,
                role = Role.RadioButton,
                onClick = onClick,
            )
            .padding(12.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        RadioButton(
            selected = selected,
            onClick = null,
        )
        Spacer(Modifier.width(8.dp))
        Text(text)
    }
}

@Composable
private fun AjustesDialogSectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        modifier = Modifier.padding(top = 16.dp, bottom = 8.dp),
    )
}

