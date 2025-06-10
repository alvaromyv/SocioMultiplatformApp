package com.amv.socioapp.ui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.amv.socioapp.model.Socio
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun MiTextField(
    label: String,
    valor: String,
    hayError: Boolean,
    mensajeError: String,
    onValorChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    esVisible: Boolean = true,
    leadingIcon: ImageVector? = null,
    trailingIcon: ImageVector? = null,
    onLeadingIconClick: () -> Unit = { },
    onTrailingIconClick: () -> Unit = { },
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    maxLines: Int = 1,
) {
    OutlinedTextField(
        label = { Text(label) },
        value = valor,
        onValueChange = { valor -> onValorChange(valor) },
        isError = hayError,
        supportingText = {
            if (hayError) {
                Text(mensajeError)
            }
        },
        modifier = modifier,
        leadingIcon =
            if (leadingIcon != null) {
                {
                    IconButton(onClick = onLeadingIconClick) {
                        Icon(imageVector = leadingIcon, contentDescription = label)
                    }
                }
            } else null,
        trailingIcon =
            if (trailingIcon != null) {
                {
                    IconButton(onClick = onTrailingIconClick) {
                        Icon(
                            imageVector =
                                if (esVisible) Icons.Filled.VisibilityOff else Icons.Filled.Visibility,
                            contentDescription = label
                        )
                    }
                }
            } else null,
        keyboardOptions = keyboardOptions,
        visualTransformation = if (esVisible) VisualTransformation.None else PasswordVisualTransformation(),
        readOnly = readOnly,
        maxLines = maxLines,
        shape = RoundedCornerShape(10.dp),
    )
}

@Composable
fun MiDoubleTextField(
    label: String,
    valor: Double,
    hayError: Boolean,
    mensajeError: String,
    onValorChange: (Double) -> Unit,
    modifier: Modifier = Modifier,
    leadingIcon: ImageVector? = null,
    onLeadingIconClick: () -> Unit = { },
) {
    OutlinedTextField(
        label = { Text(label) },
        value = valor.toString(),
        onValueChange = { valor: String -> onValorChange(valor.toDoubleOrNull() ?: 0.0) },
        leadingIcon = {
            if (leadingIcon != null) {
                IconButton(onClick = onLeadingIconClick) {
                    Icon(imageVector = leadingIcon, contentDescription = label)
                }
            } else null
        },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        maxLines = 1,
        shape = RoundedCornerShape(10.dp),
        modifier = modifier,
        isError = hayError,
        supportingText = {
            if (hayError) {
                Text(mensajeError)
            }
        }
    )
}

@Composable
fun MiCheckBox(
    valor: Boolean,
    label: String,
    onValorChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    Checkbox(
        checked = valor,
        onCheckedChange = { valor -> onValorChange(valor) },
        modifier = modifier
    )
    Text(
        text = label,
        modifier = modifier,
    )
}

@Composable
fun MiButton(
    accion: String,
    onClick: () -> Unit,
    activado: Boolean = true,
    modifier: Modifier = Modifier
) {
    Button(
        onClick = onClick,
        enabled = activado,
        shape = RoundedCornerShape(32),
        modifier = modifier
    ) {
        Text(accion)
    }
}

@Composable
inline fun <reified T> SeleccionButtonRow(
    valor: T,
    crossinline onValorChange: (T) -> Unit,
    modifier: Modifier
) where T : Enum<T> {
    val elementos = enumValues<T>().toList()
    var seleccionado = elementos.indexOf(valor)

    SingleChoiceSegmentedButtonRow(modifier = modifier) {
        elementos.forEachIndexed { indice, valor ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = indice,
                    count = elementos.size
                ),
                onClick = {
                    seleccionado = indice
                    onValorChange(valor)
                },
                selected = indice == seleccionado,
                label = { Text(valor.toString()) }
            )
        }
    }
}

@Composable
fun MiTextFieldFecha(
    label: String,
    fecha: LocalDateTime,
    hayError: Boolean,
    onFechaChange: (LocalDateTime) -> Unit,
    modifier: Modifier = Modifier
) {
    var mostrarDialogo by remember { mutableStateOf(false) }

    MiTextField(
        label = label,
        valor = Socio.formatearFecha(fecha),
        leadingIcon = Icons.Filled.DateRange,
        onLeadingIconClick = { mostrarDialogo = true },
        readOnly = true,
        onValorChange = {},
        hayError = hayError,
        mensajeError = "",
        modifier = modifier
    )

    if (mostrarDialogo) {
        SeleccionarFecha(onSeleccionarFecha = { milisegundos ->
            milisegundos?.let {
                onFechaChange(Instant.fromEpochMilliseconds(milisegundos)
                    .toLocalDateTime(TimeZone.currentSystemDefault()))
            }
        }, onRechazar = { mostrarDialogo = false })
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun SeleccionarFecha(
    onSeleccionarFecha: (Long?) -> Unit,
    onRechazar: () -> Unit
) {
    val estadoDatePicker = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)

    DatePickerDialog(
        onDismissRequest = onRechazar,
        confirmButton = {
            TextButton(onClick = {
                onSeleccionarFecha(estadoDatePicker.selectedDateMillis)
                onRechazar()
            }) {
                Text("Seleccionar")
            }
        },
        dismissButton = {
            TextButton(onClick = onRechazar) {
                Text(text = "Cancelar")
            }
        },
    ) {
        DatePicker(state = estadoDatePicker)
    }
}
