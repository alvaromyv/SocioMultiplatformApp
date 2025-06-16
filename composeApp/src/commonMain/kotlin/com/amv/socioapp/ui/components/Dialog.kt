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
import com.hyperether.resources.stringResource
import sociomultiplatformapp.composeapp.generated.resources.Res
import sociomultiplatformapp.composeapp.generated.resources.*

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
                Text(stringResource(Res.string.confirmar))
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onRechazarRequest()
                }
            ) {
                Text(stringResource(Res.string.cancelar))
            }
        }
    )
}

@Composable
fun AjustesDialog(
    onDismiss: () -> Unit,
    onCerrarSesion: () -> Unit,
    onReasignarNumeracion: () -> Unit,
    esAdmin: Boolean = false
) {
    var mostrarDialogoReasignacion by remember { mutableStateOf(false) }

    AlertDialog(
        properties = DialogProperties(usePlatformDefaultWidth = false),
        modifier = Modifier.responsiveFillMaxWidth(),
        onDismissRequest = { onDismiss() },
        title = {
            Text(
                text = stringResource(Res.string.ajustes),
                style = MaterialTheme.typography.titleLarge,
            )
        },
        text = {
            HorizontalDivider()
            Column(Modifier.verticalScroll(rememberScrollState())) {
                AjustesDialogSectionTitle(text = stringResource(Res.string.tema))
                Column(Modifier.selectableGroup()) {
                    AjustesDialogEscogerRow(
                        text = stringResource(Res.string.mismo_dispositivo),
                        selected = AjustesManager.tema == AppThemeMode.SYSTEM,
                        onClick = { AjustesManager.onTemaChange(AppThemeMode.SYSTEM) },
                    )
                    AjustesDialogEscogerRow(
                        text = stringResource(Res.string.tema_claro),
                        selected = AjustesManager.tema == AppThemeMode.LIGHT,
                        onClick = { AjustesManager.onTemaChange(AppThemeMode.LIGHT)  },
                    )
                    AjustesDialogEscogerRow(
                        text = stringResource(Res.string.tema_oscuro),
                        selected = AjustesManager.tema == AppThemeMode.DARK,
                        onClick = { AjustesManager.onTemaChange(AppThemeMode.DARK) },
                    )
                }
                AjustesDialogSectionTitle(text =  stringResource(Res.string.idioma))
                Column(Modifier.selectableGroup()) {
                    AjustesDialogEscogerRow(
                        text = stringResource(Res.string.ingles),
                        selected = AjustesManager.idioma == AppLanguage.ENGLISH,
                        onClick = { AjustesManager.onIdiomaChange(AppLanguage.ENGLISH)  },
                    )
                    AjustesDialogEscogerRow(
                        text = stringResource(Res.string.español),
                        selected = AjustesManager.idioma == AppLanguage.SPANISH,
                        onClick = { AjustesManager.onIdiomaChange(AppLanguage.SPANISH)  },
                    )
                }
                HorizontalDivider(Modifier.padding(top = 8.dp, bottom = 24.dp))
                if (esAdmin) {
                    MiButton(
                        accion = stringResource(Res.string.re_asignar_numeracion_titulo),
                        onClick = { mostrarDialogoReasignacion = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                MiButton(
                    accion = stringResource(Res.string.cerrar_sesion),
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
                    text = stringResource(Res.string.ok),
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
            titulo = stringResource(Res.string.re_asignar_numeracion_titulo),
            texto = stringResource(Res.string.re_asignar_numeracion_texto)
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

